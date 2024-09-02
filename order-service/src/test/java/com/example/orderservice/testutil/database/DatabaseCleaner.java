package com.example.orderservice.testutil.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DatabaseCleaner {

    @PersistenceContext
    private EntityManager entityManager;
    private List<String> tableNames = null; // 캐싱해놓고 쓰자
    @Transactional
    public void cleanAll() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate(); // 외래 키 제약 조건 비활성화

        if (tableNames == null) {
            tableNames = getAllTableNames();
        }

        truncateTable();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate(); // 외래 키 제약 조건 활성화
    }

    private List<String> getAllTableNames() {
        @SuppressWarnings("unchecked")
        List<String> tableNames = entityManager
                .createNativeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'")
                .getResultList();

        return tableNames;
    }

    private void truncateTable() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate(); // 외래 키 제약 조건 비활성화

        for (String tableName : tableNames) {
            if (tableName.equals("flyway_schema_history")) continue;
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate(); // 외래 키 제약 조건 활성화
    }
}
