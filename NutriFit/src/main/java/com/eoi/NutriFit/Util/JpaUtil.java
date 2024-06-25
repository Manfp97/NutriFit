package com.eoi.NutriFit.Util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory entityManagerFactory = formaEntityManagerFactory();

    private static EntityManagerFactory formaEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("JpaUtil");
    }

    public static EntityManager getEntitymanager() {
        return entityManagerFactory.createEntityManager();
    }
}
