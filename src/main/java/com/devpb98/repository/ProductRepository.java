package com.devpb98.repository;

import com.devpb98.config.DynamoDBEnhancedConfig;
import com.devpb98.entity.Product;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private DynamoDbTable<Product> productDynamoDbTable;

    @PostConstruct
    public void init() {
        productDynamoDbTable = dynamoDbEnhancedClient.table("Product", TableSchema.fromBean(Product.class));
    }

    public Product save(Product product) {
        productDynamoDbTable.putItem(product);

        return product;
    }

    public Product findById(String id) {
        return productDynamoDbTable.getItem( r -> r.key(k -> k.partitionValue(id)));
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        productDynamoDbTable.scan().items().forEach(products::add);
        return products;
    }

    public void deleteById(String id) {
        productDynamoDbTable.deleteItem(k -> k.key(r -> r.partitionValue(id)));
    }
}
