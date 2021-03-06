package com.example.ecommerceasm.seeder;

import com.example.ecommerceasm.entity.*;
import com.example.ecommerceasm.enums.OrderStatus;
import com.example.ecommerceasm.enums.ProductStatus;
import com.example.ecommerceasm.enums.UserStatus;
import com.example.ecommerceasm.repository.CateRepository;
import com.example.ecommerceasm.repository.OrderRepository;
import com.example.ecommerceasm.repository.ProductRepository;
import com.example.ecommerceasm.repository.UserRepository;
import com.example.ecommerceasm.util.DateTimeHelper;
import com.example.ecommerceasm.util.NumberUtil;
import com.example.ecommerceasm.util.RandomLocalDateTime;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataSeeder implements CommandLineRunner {

    boolean createSeedData = false;
    final ProductRepository productRepository;
    final CateRepository cateRepository;
    final OrderRepository orderRepository;
    final UserRepository userRepository;
    Faker faker;
    Random random = new Random();

    int numberOfProduct = 500;
    int numberOfOrder = 10000;
    int numberOfUser = 100;

    public DataSeeder(ProductRepository productRepository, CateRepository cateRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cateRepository = cateRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {
        if (createSeedData) {
            productRepository.deleteAll();
            cateRepository.deleteAll();
            orderRepository.deleteAll();
            userRepository.deleteAll();
            seedCate();
            seedProduct();
            seedUser();
            seedOrder();
        }
    }


    private void seedCate() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("C???"));
        categories.add(new Category("C??y"));
        categories.add(new Category("Hoa"));
        categories.add(new Category("L??"));
        categories.add(new Category("C??nh"));
        cateRepository.saveAll(categories);
    }

    private void seedProduct() {
        List<Product> listProduct = new ArrayList<>();
        List<Category> categories = cateRepository.findAll();
        for (int i = 0; i < numberOfProduct; i++) {
            Product product = new Product();
            product.setId(UUID.randomUUID().toString());
            product.setName(faker.funnyName().name());
            product.setDescription(faker.lorem().paragraph());
            product.setQuantity(faker.number().numberBetween(10, 100));
            product.setPrice(BigDecimal.valueOf(faker.number().numberBetween(50, 200) * 100));
            product.setCate(categories.get(random.nextInt(categories.size())));
            product.setDetail(faker.lorem().paragraph());
            product.setThumbnails(faker.avatar().image());
            product.setStatus(ProductStatus.AVAILABLE);
            product.setCreatedAt(RandomLocalDateTime.generateLocalDate());
            listProduct.add(product);
        }
        productRepository.saveAll(listProduct);
    }

    private void seedUser() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numberOfUser; i++) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setFullname(faker.name().fullName());
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setEmail(faker.name().username() + "@gmail.com");
            user.setAddress(faker.address().streetAddress());
            user.setCity(faker.address().cityName());
            user.setDistrict(faker.address().streetName());
            user.setWard(faker.address().state());
            user.setStatus(UserStatus.ACTIVE);
            users.add(user);
        }
        userRepository.saveAll(users);
    }

    private void seedOrder() {
        List<Product> products = productRepository.findAll();
        List<User> users = userRepository.findAll();
        // Sinh danh s??ch order ????? l??u
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < numberOfOrder; i++) {
            // Th??ng tin m???t order
            Order order = new Order();
            User user = users.get(random.nextInt(users.size()));
            order.setId(UUID.randomUUID().toString());
            order.setUser(user); // ch??a c?? userId, t???m th???i ????? m???c ?????nh.
            // Generate order details.
            Set<OrderDetail> orderDetails = new HashSet<>();
            // sinh ng???u nhi??n s??? l?????ng order detail cho m???t ????n h??ng.
            int randomOrderDetailNumber = faker.number().numberBetween(1, 5);
            HashSet<String> existingProductId = new HashSet<>();
            for (int j = 0; j < randomOrderDetailNumber; j++) {
                // Generate 1 item
                OrderDetail orderDetail = new OrderDetail();
                // l???y random s???n ph???m t??? trong danh s??ch.
                Product randomProduct = products.get(
                        faker.number().numberBetween(0, products.size() - 1));
                // tr??nh t??nh tr???ng s???n ph???m b??? tr??ng trong ????n h??ng.
                if (existingProductId.contains(randomProduct.getId())) {
                    // b??? qua n???u tr??ng s???n ph???m ho???c c?? th??? l???y random l???i m???t product kh??c.
                    continue;
                }
                existingProductId.add(randomProduct.getId().toString());
                // t???o kho?? ch??nh t??? order id v?? product id
                orderDetail.setId(new OrderDetailId(order.getId(), randomProduct.getId()));
                // set quan h??? v???i order
                orderDetail.setOrder(order);
                // set quan h??? v???i product
                orderDetail.setProduct(randomProduct);
                orderDetail.setUnitPrice(randomProduct.getPrice());
                orderDetail.setQuantity(faker.number().numberBetween(1, 5));
                // t??nh l???i t???ng ti???n.
                order.addTotalPrice(orderDetail);
                // add v??o danh s??ch order Detail
                orderDetails.add(orderDetail);
            }
            // set orderDetails v??o order
            order.setOrderDetails(orderDetails);
            order.setStatus(OrderStatus.DONE);
            order.setCreatedAt(RandomLocalDateTime.generateLocalDate());
            // Add order v??o danh s??ch orders b??n ngo??i, ????? c?? th??? save all.
            orders.add(order);
        }
        orderRepository.saveAll(orders);
    }

    private LocalDateTime calculateOrderCreatedTime(OrderSeedByTime orderSeedByTime) {
        LocalDateTime result = null;
        LocalDateTime tempLocalDateTime = null;
        int tempMonth = 1;
        int tempYear = 2022;
        switch (orderSeedByTime.getSeedTypeByTime()) {
            case YEAR:
                // n???u theo n??m th?? random th??ng v?? ng??y.
                tempMonth = DateTimeHelper.getRandomMonth().getValue();
                tempYear = orderSeedByTime.getYear();
                tempLocalDateTime = LocalDateTime.of(tempYear, tempMonth, 1, 0, 0, 0);
                result = tempLocalDateTime.plusMonths(1).minusDays(1);
                break;
            case MONTH:
                // n???u theo th??ng, n??m th?? random ng??y.
                tempMonth = orderSeedByTime.getMonth().getValue();
                tempYear = orderSeedByTime.getYear();
                tempLocalDateTime = LocalDateTime.of(tempYear, tempMonth, 1, 0, 0, 0);
                LocalDateTime lastDayOfMonth = tempLocalDateTime.plusMonths(1).minusDays(1);
                int randomDay = NumberUtil.getRandomNumber(1, lastDayOfMonth.getDayOfMonth());
                result = LocalDateTime.of(tempYear, tempMonth, randomDay, 0, 0, 0);
                if (result.isAfter(LocalDateTime.now())) {
                    // n???u sau th???i gian hi???n t???i, t???c l?? th??ng n??m ??ang th???i gian hi???n t???i
                    randomDay = NumberUtil.getRandomNumber(1, LocalDateTime.now().getDayOfMonth());
                    result = LocalDateTime.of(tempYear, tempMonth, randomDay, 0, 0, 0);
                }
                break;
            case DAY:
                // n???u l?? ng??y th?? fix
                result = LocalDateTime.of(orderSeedByTime.getYear(), orderSeedByTime.getMonth(), orderSeedByTime.getDay(), 0, 0, 0);
                break;
        }
        return result;
    }
}
