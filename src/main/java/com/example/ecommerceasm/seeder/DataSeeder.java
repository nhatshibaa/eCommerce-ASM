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
        categories.add(new Category("Cỏ"));
        categories.add(new Category("Cây"));
        categories.add(new Category("Hoa"));
        categories.add(new Category("Lá"));
        categories.add(new Category("Cành"));
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
        // Sinh danh sách order để lưu
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < numberOfOrder; i++) {
            // Thông tin một order
            Order order = new Order();
            User user = users.get(random.nextInt(users.size()));
            order.setId(UUID.randomUUID().toString());
            order.setUser(user); // chưa có userId, tạm thời để mặc định.
            // Generate order details.
            Set<OrderDetail> orderDetails = new HashSet<>();
            // sinh ngẫu nhiên số lượng order detail cho một đơn hàng.
            int randomOrderDetailNumber = faker.number().numberBetween(1, 5);
            HashSet<String> existingProductId = new HashSet<>();
            for (int j = 0; j < randomOrderDetailNumber; j++) {
                // Generate 1 item
                OrderDetail orderDetail = new OrderDetail();
                // lấy random sản phẩm từ trong danh sách.
                Product randomProduct = products.get(
                        faker.number().numberBetween(0, products.size() - 1));
                // tránh tình trạng sản phẩm bị trùng trong đơn hàng.
                if (existingProductId.contains(randomProduct.getId())) {
                    // bỏ qua nếu trùng sản phẩm hoặc có thể lấy random lại một product khác.
                    continue;
                }
                existingProductId.add(randomProduct.getId().toString());
                // tạo khoá chính từ order id và product id
                orderDetail.setId(new OrderDetailId(order.getId(), randomProduct.getId()));
                // set quan hệ với order
                orderDetail.setOrder(order);
                // set quan hệ với product
                orderDetail.setProduct(randomProduct);
                orderDetail.setUnitPrice(randomProduct.getPrice());
                orderDetail.setQuantity(faker.number().numberBetween(1, 5));
                // tính lại tổng tiền.
                order.addTotalPrice(orderDetail);
                // add vào danh sách order Detail
                orderDetails.add(orderDetail);
            }
            // set orderDetails vào order
            order.setOrderDetails(orderDetails);
            order.setStatus(OrderStatus.DONE);
            order.setCreatedAt(RandomLocalDateTime.generateLocalDate());
            // Add order vào danh sách orders bên ngoài, để có thể save all.
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
                // nếu theo năm thì random tháng và ngày.
                tempMonth = DateTimeHelper.getRandomMonth().getValue();
                tempYear = orderSeedByTime.getYear();
                tempLocalDateTime = LocalDateTime.of(tempYear, tempMonth, 1, 0, 0, 0);
                result = tempLocalDateTime.plusMonths(1).minusDays(1);
                break;
            case MONTH:
                // nếu theo tháng, năm thì random ngày.
                tempMonth = orderSeedByTime.getMonth().getValue();
                tempYear = orderSeedByTime.getYear();
                tempLocalDateTime = LocalDateTime.of(tempYear, tempMonth, 1, 0, 0, 0);
                LocalDateTime lastDayOfMonth = tempLocalDateTime.plusMonths(1).minusDays(1);
                int randomDay = NumberUtil.getRandomNumber(1, lastDayOfMonth.getDayOfMonth());
                result = LocalDateTime.of(tempYear, tempMonth, randomDay, 0, 0, 0);
                if (result.isAfter(LocalDateTime.now())) {
                    // nếu sau thời gian hiện tại, tức là tháng năm đang thời gian hiện tại
                    randomDay = NumberUtil.getRandomNumber(1, LocalDateTime.now().getDayOfMonth());
                    result = LocalDateTime.of(tempYear, tempMonth, randomDay, 0, 0, 0);
                }
                break;
            case DAY:
                // nếu là ngày thì fix
                result = LocalDateTime.of(orderSeedByTime.getYear(), orderSeedByTime.getMonth(), orderSeedByTime.getDay(), 0, 0, 0);
                break;
        }
        return result;
    }
}
