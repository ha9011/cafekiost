package sample.cafekiosk.spring.api.service.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateReq;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderResponse createOrder(OrderCreateReq req, LocalDateTime registeredDateTime) {
        List<String> productNumbers = req.getProductNumbers();
        List<Product> products = getDuplicateProducts(productNumbers);


        // 재고차감 체크가 필요한 상품들 filter
        //
//        products.stream().filter(p-> !p.getType().equals(ProductType.HANDMADE))
//                .collect(Collectors.toList());

        // enum이 상태값을 관리 하는 거니 enum 클레스에서 만들자(withStockType)
        List<String> stockProductNumbers = products.stream().filter(p -> ProductType.withStockType(p.getType()))
                .map(Product::getProductNumber)
                .collect(Collectors.toList());


        // 재고 엔티티 조회.
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        Map<String, Stock> stockMap = stocks.stream().collect(Collectors.toMap(Stock::getProductNumber, s -> s));

        // 상품별 카운팅
        Map<String, Long> productCountingMap = stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        // 재고차감 시도
        for (String stockProductNumber : productCountingMap.keySet()) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();
            if(stock.isQuantityLessThan(quantity)){
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }

        List<Stock> stockss = stockRepository.findAll();
        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> getDuplicateProducts(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream().collect(Collectors.toMap(Product::getProductNumber, p -> p));
        return productNumbers.stream().map(productMap::get).collect(Collectors.toList());
    }
}
