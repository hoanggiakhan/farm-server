package com.farm.farm_manager.security;
public class Endpoints {
    public static final String[] adminGet = {
            "/animal",
            "/employee/**",
            "/crop",
            "/animals/**",
            "crops/**",
            "/employees/**",
            "/employees",
            "/farms/**",
            "/farm/crops/**",
            "/farm/transactions/**",
            "/farm/animals/**",
            "/farm/employees/**",
            "/farm/inventories/**",
            "/farm/role/**",
            "/inventory/items/**",
            "/task/**",
            "/crop/**",
            "/harvest/**",
    };
    public static final String[] publicGet = {
            "/employee/get-attendances/**",
            "/employee/notifications/**",
            "/employee/task/**",
            "/farm/animals/**",
    };

    public static final String[] adminPost = {
            "/employee/**",
            "/inventory/add-item/**",
            "/task/**",
            "/farm",
            "/inventory/add-inventory/**",
            "/role/**",
            "/crop/**",
            "/animal/**",
            "/harvest/add-harvest/**",
    };
    public static final String[] publicPost = {
            "/employee/check-in/**",
            "/employee/login"
    };
    public static final String[] adminDelete = {
            "/inventory/delete-item/**",
            "/inventory/delete-inventory/**",
            "/task/delete-task/**",
            "/employee/delete-employee/**",
            "/crop/delete-crop/**",
            "/animal/delete-animal/**",
            "/employee/delete-notifications/**"
    };

    public static final String[] adminPut = {
            "/harvest/sell-products/**",
            "/employee/read/**",
            "/animal/sell-animal/{animalId}/**",
            "/employee/check-out/**",
            "/crop/update/**",
            "/employee/update-user/**",
            "/employee/total-salary/**"
    };

    public static final String[] publicPut = {
            "/employee/check-out/**",
            "/employee/task/complete/**",
    };
}
