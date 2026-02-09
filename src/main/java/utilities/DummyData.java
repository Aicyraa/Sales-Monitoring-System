package utilities;

public class DummyData {

        // Format: {Name, Category, Supplier, Price, Cost, Stock, ReorderLevel}
        // IDs will be assigned 1 to 6 in order
        static String[][] dummyProduct = {
                        { "Pencil", "Stationery", "Aali", "15.00", "10.00", "100", "20" }, // ID: 1
                        { "Notebook", "Stationery", "BookWorld", "50.00", "35.00", "80", "15" }, // ID: 2
                        { "Eraser", "Stationery", "Jiana", "5.00", "3.00", "100", "20" }, // ID: 3
                        { "Bond Paper (Pack)", "Paper", "JR", "150.00", "120.00", "50", "10" }, // ID: 4
                        { "Ballpen", "Stationery", "Aali", "20.00", "15.00", "100", "20" }, // ID: 5
                        { "Protractor", "Math Tools", "Jiana", "25.00", "18.00", "40", "10" } // ID: 6
        };

        // Format: {productId, productName, quantitySold, unitPrice, discountPercentage,
        // seasonTag, dateTime}
        static String[][] dummySales = {
                        // Recent (Feb 9)
                        { "1", "Pencil", "80", "15.00", "0", "Regular", "2026-02-09T09:00:00" },
                        { "2", "Notebook", "2", "50.00", "0", "Regular", "2026-02-09T09:30:00" },
                        { "5", "Ballpen", "3", "20.00", "0", "Regular", "2026-02-09T10:15:00" },
                        { "4", "Bond Paper (Pack)", "1", "150.00", "0", "Regular", "2026-02-09T11:00:00" },

                        // Yesterday (Feb 8)
                        { "1", "Pencil", "10", "15.00", "0", "Regular", "2026-02-08T14:00:00" },
                        { "3", "Eraser", "5", "5.00", "0", "Regular", "2026-02-08T15:30:00" },
                        { "6", "Protractor", "2", "25.00", "10", "Back to School", "2026-02-08T16:45:00" },

                        // Earlier this week
                        { "2", "Notebook", "5", "50.00", "10", "Promo", "2026-02-07T10:00:00" },
                        { "5", "Ballpen", "8", "20.00", "0", "Regular", "2026-02-07T13:20:00" },
                        { "1", "Pencil", "15", "15.00", "0", "Bulk", "2026-02-06T09:45:00" },
                        { "4", "Bond Paper (Pack)", "2", "150.00", "5", "Promo", "2026-02-05T11:30:00" },
                        { "3", "Eraser", "10", "5.00", "0", "Regular", "2026-02-04T14:15:00" },
                        { "6", "Protractor", "1", "25.00", "0", "Regular", "2026-02-03T16:00:00" },
                        { "2", "Notebook", "3", "50.00", "0", "Regular", "2026-02-02T10:30:00" },
                        { "1", "Pencil", "20", "15.00", "20", "Clearance", "2026-02-01T12:00:00" }
        };

        public static String[][] getDummyProduct() {
                return dummyProduct;
        }

        public static String[][] getDummySales() {
                return dummySales;
        }

}