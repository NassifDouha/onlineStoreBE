package com.ecom.eilco.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecom.eilco.model.Category;
import com.ecom.eilco.model.Product;
import com.ecom.eilco.repository.CategoryRepository;
import com.ecom.eilco.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) {

        // Define categories
        List<Map<String, String>> categories = List.of(
            Map.of("name", "Électroniques", "description", "Produits électroniques modernes et innovants.", "imageUrl", "https://www.polyvia.fr/sites/default/files/styles/image_detail/public/2021-05/3000-800-puces-eletroniques.jpg?itok=BL84_uKP"),
            Map.of("name", "Mode", "description", "Vêtements, chaussures et accessoires tendance.", "imageUrl", "https://plus.unsplash.com/premium_photo-1664202526559-e21e9c0fb46a?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            Map.of("name", "Maison & Cuisine", "description", "Articles pour la maison et ustensiles de cuisine.", "imageUrl", "https://images.unsplash.com/photo-1556909212-d5b604d0c90d?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            Map.of("name", "Sport & Loisirs", "description", "Équipements et accessoires sportifs.", "imageUrl", "https://plus.unsplash.com/premium_photo-1684274186183-a436f2458aa4?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            Map.of("name", "Beauté & Santé", "description", "Produits de beauté et bien-être.", "imageUrl", "https://images.unsplash.com/photo-1540555700478-4be289fbecef?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
        );

        // Insert categories if not exists
        for (Map<String, String> data : categories) {
            categoryRepository.findByName(data.get("name")).orElseGet(() -> {
                Category category = Category.builder()
                        .name(data.get("name"))
                        .description(data.get("description"))
                        .imageUrl(data.get("imageUrl"))
                        .build();
                return categoryRepository.save(category);
            });
        }

        // Define products
        List<Map<String, Object>> products = List.of(
            Map.of("name", "Smartphone Galaxy S21", "description", "Téléphone haut de gamme avec écran AMOLED.", "imageUrl", "https://m.media-amazon.com/images/I/81g7zh69wYL.jpg", "price", new BigDecimal("899.99"), "quantity", 50, "category", "Électroniques"),
            Map.of("name", "Ordinateur Portable Dell XPS", "description", "Laptop puissant pour professionnels et gamers.", "imageUrl", "https://i.dell.com/is/image/DellContent/content/dam/ss2/product-images/dell-client-products/notebooks/xps-notebooks/xps-17-9730/media-gallery/notebook-xps-17-9730-t-gray-gallery-5.psd?fmt=pjpg&pscan=auto&scl=1&wid=3500&hei=2059&qlt=100,1&resMode=sharp2&si", "price", new BigDecimal("1499.99"), "quantity", 30, "category", "Électroniques"),
            Map.of("name", "Casque Bluetooth Bose", "description", "Casque audio avec réduction de bruit active.", "imageUrl", "https://static.fnac-static.com/multimedia/Images/FR/MDM/a0/17/52/22157216/3756-1/tsp20250121153127/Casque-circum-aural-sans-fil-Bluetooth-Bose-QuietComfort-a-reduction-de-bruit-Noir.jpg", "price", new BigDecimal("299.99"), "quantity", 40, "category", "Électroniques"),
            Map.of("name", "Télévision OLED LG 55\"", "description", "TV OLED 4K ultra haute définition.", "imageUrl", "https://www.lg.com/content/dam/channel/wcms/ch_fr/images/televiseurs/oled55c37la_avs_eedg_ch_fr_c/gallery/TV-OLED-55-C3-A-Gallery-01-1600.jpg", "price", new BigDecimal("1299.99"), "quantity", 20, "category", "Électroniques"),
            Map.of("name", "Montre Connectée Apple Watch", "description", "Montre intelligente avec suivi de la santé.", "imageUrl", "https://media.ldlc.com/r1600/ld/products/00/06/16/75/LD0006167543_0006167548.jpg", "price", new BigDecimal("399.99"), "quantity", 60, "category", "Mode"),
            Map.of("name", "Sac à main en cuir", "description", "Sac à main élégant en cuir véritable.", "imageUrl", "https://dionecouture.com/cdn/shop/files/hera-grand-sac-a-main-en-cuir-souple-khaki-sac-a-main_1_1.jpg?v=1728227919", "price", new BigDecimal("249.99"), "quantity", 25, "category", "Mode"),
            Map.of("name", "Chaussures de sport Nike", "description", "Chaussures de course légères et confortables.", "imageUrl", "https://media.intersport.fr/is/image/intersportfr/FB7689_DBU_Q1?$produit_l$&$product_grey$", "price", new BigDecimal("119.99"), "quantity", 80, "category", "Mode"),
            Map.of("name", "Veste en jean Levi’s", "description", "Veste classique en jean, coupe moderne.", "imageUrl", "https://www.boysdiffusion.com/27382-large/veste-en-jean-brut-levis.jpg", "price", new BigDecimal("89.99"), "quantity", 35, "category", "Mode"),
            Map.of("name", "Machine à café Nespresso", "description", "Cafetière automatique avec capsules.", "imageUrl", "https://www.best-espresso.com/825-large_default/machine-nespresso-inissia-rouge.jpg", "price", new BigDecimal("179.99"), "quantity", 45, "category", "Maison & Cuisine"),
            Map.of("name", "Aspirateur Dyson V11", "description", "Aspirateur sans fil puissant.", "imageUrl", "https://cdn.laredoute.com/products/7/2/0/7200779a63b80256932436d3f171471e.jpg", "price", new BigDecimal("599.99"), "quantity", 15, "category", "Maison & Cuisine"),
            Map.of("name", "Casserole en acier inoxydable", "description", "Idéal pour la cuisine quotidienne.", "imageUrl", "https://www.lejeune.tm.fr/media/catalog/product/cache/f2f4f978024a18bdc8affcd708beb594/c/a/casseroles-combi-empillable.jpg", "price", new BigDecimal("39.99"), "quantity", 70, "category", "Maison & Cuisine"),
            Map.of("name", "Mixeur Blender Philips", "description", "Blender performant pour smoothies et soupes.", "imageUrl", "https://media.rueducommerce.fr/mktp/product/95be/5d36/63db/4e4f/8b68/16f2/4ba3/95be5d3663db4e4f8b6816f24ba36f85.webp", "price", new BigDecimal("79.99"), "quantity", 50, "category", "Maison & Cuisine"),
            Map.of("name", "Vélo de route Giant", "description", "Vélo de course ultraléger.", "imageUrl", "https://www.intercycle.fr/Files/124649/Img/17/Velo-Route-GIANT-Propel-Advanced-Pro-1-Bleu-Metallique-zoom.jpg", "price", new BigDecimal("1299.99"), "quantity", 10, "category", "Sport & Loisirs"),
            Map.of("name", "Tapis de yoga antidérapant", "description", "Tapis de sport épais pour yoga et fitness.", "imageUrl", "https://defaugeres.eu/fichiers/produits/prod-513-1_g.jpg", "price", new BigDecimal("49.99"), "quantity", 100, "category", "Sport & Loisirs"),
            Map.of("name", "Haltères réglables", "description", "Paire d’haltères ajustables de 2 à 20 kg.", "imageUrl", "https://actiexpress.fr/844-large_default/haltere-reglable-par-molette-5-40kg.jpg", "price", new BigDecimal("199.99"), "quantity", 40, "category", "Sport & Loisirs"),
            Map.of("name", "Raquette de tennis Wilson", "description", "Raquette légère et maniable pour joueurs de tous niveaux.", "imageUrl", "https://www.tennis-point.fr/dw/image/v2/BBDP_PRD/on/demandware.static/-/Sites-master-catalog/default/dw1d5d52d3/images/007/062/03876000_000.jpg?q=80&sw=2000", "price", new BigDecimal("129.99"), "quantity", 20, "category", "Sport & Loisirs"),
            Map.of("name", "Crème hydratante Nivea", "description", "Crème visage nourrissante pour peau douce.", "imageUrl", "https://www.monvanityideal.com/data/produits/37/43/u/nivea-care-nutrition-intense-2e42e081a56248f1850798a49c491102.jpg", "price", new BigDecimal("12.99"), "quantity", 200, "category", "Beauté & Santé"),
            Map.of("name", "Brosse à dents électrique Oral-B", "description", "Brosse à dents avec capteurs intelligents.", "imageUrl", "https://static.fnac-static.com/multimedia/Images/FR/MDM/ca/99/2e/19831242/3756-1/tsp20250114214213/Broe-a-dents-electrique-Oral-B-Vitality-Pro-D173-Noir.jpg", "price", new BigDecimal("89.99"), "quantity", 50, "category", "Beauté & Santé"),
            Map.of("name", "Parfum Dior Sauvage", "description", "Eau de toilette pour homme, fraîche et intense.", "imageUrl", "https://www.sephora.fr/dw/image/v2/BCVW_PRD/on/demandware.static/-/Sites-masterCatalog_Sephora/default/dw07744eb1/images/hi-res/alternates/PID_alternate1/PID_alternate1_3316/P10017596_1.jpg?sw=265&sh=265&sm=fit", "price", new BigDecimal("99.99"), "quantity", 30, "category", "Beauté & Santé"),
            Map.of("name", "Kit de maquillage professionnel", "description", "Palette de maquillage complète pour toutes occasions.", "imageUrl", "https://m.media-amazon.com/images/I/81N6Et9eimL._AC_UF894,1000_QL80_.jpg", "price", new BigDecimal("59.99"), "quantity", 40, "category", "Beauté & Santé")
        );

        // Insert products if not exists
        for (Map<String, Object> data : products) {
            String categoryName = (String) data.get("category");
            Optional<Category> categoryOpt = categoryRepository.findByName(categoryName);

            categoryOpt.ifPresent(category -> {
                productRepository.findByName((String) data.get("name")).orElseGet(() -> {
                    Product product = Product.builder()
                            .name((String) data.get("name"))
                            .description((String) data.get("description"))
                            .imageUrl((String) data.get("imageUrl"))
                            .price((BigDecimal) data.get("price"))
                            .quantity((Integer) data.get("quantity"))
                            .active(true)
                            .category(category)
                            .build();
                    return productRepository.save(product);
                });
            });
        }

        System.out.println("Database seeding completed!");
    }
}
