package utilities;

/**
 * Provides dummy/sample data for products and sales.
 * Used to populate the system with initial test data.
 */
public class DummyData {

        // Format: {Name, Category, Supplier, Price, Cost, Stock, ReorderLevel}
        // IDs will be assigned sequentially
        static String[][] dummyProduct = {
                // Stationery Items
                { "Pencil", "Stationery", "Aali", "15.00", "10.00", "500", "20" }, // ID: 1
                { "Notebook", "Stationery", "Jee", "50.00", "35.00", "300", "15" }, // ID: 2
                { "Eraser", "Stationery", "Jiana", "5.00", "3.00", "400", "20" }, // ID: 3
                { "Ballpen", "Stationery", "Aali", "20.00", "15.00", "450", "20" }, // ID: 5
                { "Permanent Marker", "Stationery", "Aali", "35.00", "25.00", "200", "15" }, // ID: 7
                { "Highlighter", "Stationery", "Jee", "30.00", "20.00", "250", "15" }, // ID: 8
                { "Correction Tape", "Stationery", "Jiana", "40.00", "28.00", "180", "12" }, // ID: 9
                { "Stapler", "Stationery", "Aali", "120.00", "85.00", "100", "10" }, // ID: 10
                { "Scissors", "Stationery", "Jiana", "80.00", "55.00", "120", "10" }, // ID: 11
                { "Glue Stick", "Stationery", "Jee", "25.00", "18.00", "200", "15" }, // ID: 12

                // Paper Products
                { "Bond Paper (Pack)", "Paper", "JR", "150.00", "120.00", "250", "10" }, // ID: 4
                { "Colored Paper (Pack)", "Paper", "Jee", "180.00", "140.00", "150", "8" }, // ID: 13
                { "Folder (Plastic)", "Paper", "JR", "12.00", "8.00", "300", "20" }, // ID: 14
                { "Envelope (Pack)", "Paper", "Jee", "45.00", "32.00", "200", "15" }, // ID: 15
                { "Index Cards (Pack)", "Paper", "JR", "55.00", "40.00", "180", "12" }, // ID: 16

                // Math Tools
                { "Protractor", "Math Tools", "Jiana", "25.00", "18.00", "200", "10" }, // ID: 6
                { "Compass", "Math Tools", "Jiana", "45.00", "32.00", "150", "10" }, // ID: 17
                { "Ruler (30cm)", "Math Tools", "Aali", "20.00", "14.00", "250", "15" }, // ID: 18
                { "Triangle Set", "Math Tools", "Jiana", "35.00", "25.00", "180", "12" }, // ID: 19
                { "Calculator", "Math Tools", "Jee", "250.00", "180.00", "100", "8" }, // ID: 20

                // Art Supplies
                { "Crayons (12 colors)", "Art Supplies", "Jee", "65.00", "45.00", "200", "15" }, // ID: 21
                { "Watercolor Set", "Art Supplies", "Jee", "150.00", "105.00", "120", "10" }, // ID: 22
                { "Oil Pastel (24 colors)", "Art Supplies", "Jee", "180.00", "130.00", "100", "8" }, // ID: 23
                { "Sketch Pad", "Art Supplies", "Jee", "85.00", "60.00", "150", "12" }, // ID: 24
                { "Paint Brush Set", "Art Supplies", "Jee", "120.00", "85.00", "130", "10" }, // ID: 25

                // Office Supplies
                { "Binder Clips (Pack)", "Office Supplies", "Aali", "35.00", "25.00", "220", "15" }, // ID: 26
                { "Paper Clips (Box)", "Office Supplies", "Aali", "28.00", "20.00", "250", "18" }, // ID: 27
                { "Tape Dispenser", "Office Supplies", "Aali", "95.00", "68.00", "140", "10" }, // ID: 28
                { "Staples (Box)", "Office Supplies", "Aali", "22.00", "16.00", "300", "20" }, // ID: 29
                { "Puncher", "Office Supplies", "Aali", "150.00", "108.00", "110", "8" }, // ID: 30
        };

        // Format: {productId, productName, quantitySold, unitPrice, discountPercentage, seasonTag, dateTime}
        static String[][] dummySales = {
                // ========== FEBRUARY 2026 ==========
                { "1", "Pencil", "80", "15.00", "0", "Regular", "2026-02-09T09:00:00" },
                { "2", "Notebook", "2", "50.00", "0", "Regular", "2026-02-09T09:30:00" },
                { "5", "Ballpen", "3", "20.00", "0", "Regular", "2026-02-09T10:15:00" },
                { "4", "Bond Paper (Pack)", "1", "150.00", "0", "Regular", "2026-02-09T11:00:00" },
                { "1", "Pencil", "10", "15.00", "0", "Regular", "2026-02-08T14:00:00" },
                { "3", "Eraser", "5", "5.00", "0", "Regular", "2026-02-08T15:30:00" },
                { "6", "Protractor", "2", "25.00", "10", "Back to School", "2026-02-08T16:45:00" },
                { "2", "Notebook", "5", "50.00", "10", "Promo", "2026-02-07T10:00:00" },
                { "5", "Ballpen", "8", "20.00", "0", "Regular", "2026-02-07T13:20:00" },
                { "1", "Pencil", "15", "15.00", "0", "Bulk", "2026-02-06T09:45:00" },
                { "4", "Bond Paper (Pack)", "2", "150.00", "5", "Promo", "2026-02-05T11:30:00" },
                { "3", "Eraser", "10", "5.00", "0", "Regular", "2026-02-04T14:15:00" },
                { "6", "Protractor", "1", "25.00", "0", "Regular", "2026-02-03T16:00:00" },
                { "2", "Notebook", "3", "50.00", "0", "Regular", "2026-02-02T10:30:00" },
                { "1", "Pencil", "20", "15.00", "20", "Clearance", "2026-02-01T12:00:00" },

                // ========== JANUARY 2026 ==========
                { "7", "Permanent Marker", "12", "35.00", "0", "Regular", "2026-01-28T10:30:00" },
                { "8", "Highlighter", "8", "30.00", "5", "Promo", "2026-01-27T14:20:00" },
                { "13", "Colored Paper (Pack)", "5", "180.00", "0", "Regular", "2026-01-26T09:15:00" },
                { "21", "Crayons (12 colors)", "15", "65.00", "0", "Regular", "2026-01-25T11:45:00" },
                { "10", "Stapler", "6", "120.00", "0", "Regular", "2026-01-24T13:30:00" },
                { "1", "Pencil", "25", "15.00", "0", "Bulk", "2026-01-23T10:00:00" },
                { "5", "Ballpen", "18", "20.00", "0", "Bulk", "2026-01-22T15:15:00" },
                { "2", "Notebook", "12", "50.00", "0", "Regular", "2026-01-21T09:30:00" },
                { "20", "Calculator", "4", "250.00", "10", "Promo", "2026-01-20T14:00:00" },
                { "4", "Bond Paper (Pack)", "8", "150.00", "0", "Regular", "2026-01-19T11:20:00" },
                { "14", "Folder (Plastic)", "20", "12.00", "0", "Regular", "2026-01-18T10:15:00" },
                { "26", "Binder Clips (Pack)", "7", "35.00", "0", "Regular", "2026-01-17T13:45:00" },
                { "11", "Scissors", "5", "80.00", "0", "Regular", "2026-01-16T09:30:00" },
                { "3", "Eraser", "12", "5.00", "0", "Regular", "2026-01-15T14:20:00" },

                // ========== DECEMBER 2025 ==========
                { "1", "Pencil", "30", "15.00", "15", "Holiday Sale", "2025-12-30T10:00:00" },
                { "2", "Notebook", "20", "50.00", "15", "Holiday Sale", "2025-12-29T14:30:00" },
                { "21", "Crayons (12 colors)", "25", "65.00", "20", "Holiday Sale", "2025-12-28T09:45:00" },
                { "22", "Watercolor Set", "10", "150.00", "20", "Holiday Sale", "2025-12-27T11:15:00" },
                { "24", "Sketch Pad", "8", "85.00", "15", "Holiday Sale", "2025-12-26T13:00:00" },
                { "5", "Ballpen", "22", "20.00", "10", "Holiday Sale", "2025-12-24T10:30:00" },
                { "3", "Eraser", "15", "5.00", "10", "Holiday Sale", "2025-12-23T15:45:00" },
                { "13", "Colored Paper (Pack)", "12", "180.00", "15", "Holiday Sale", "2025-12-22T09:20:00" },
                { "4", "Bond Paper (Pack)", "10", "150.00", "10", "Holiday Sale", "2025-12-21T14:10:00" },
                { "20", "Calculator", "6", "250.00", "15", "Holiday Sale", "2025-12-20T11:30:00" },
                { "7", "Permanent Marker", "14", "35.00", "10", "Holiday Sale", "2025-12-19T10:15:00" },
                { "8", "Highlighter", "12", "30.00", "10", "Holiday Sale", "2025-12-18T13:45:00" },
                { "25", "Paint Brush Set", "9", "120.00", "15", "Holiday Sale", "2025-12-17T09:00:00" },
                { "17", "Compass", "8", "45.00", "10", "Holiday Sale", "2025-12-16T14:30:00" },

                // ========== NOVEMBER 2025 ==========
                { "26", "Binder Clips (Pack)", "10", "35.00", "0", "Regular", "2025-11-28T09:30:00" },
                { "27", "Paper Clips (Box)", "12", "28.00", "0", "Regular", "2025-11-27T14:15:00" },
                { "28", "Tape Dispenser", "5", "95.00", "0", "Regular", "2025-11-26T10:45:00" },
                { "1", "Pencil", "20", "15.00", "0", "Regular", "2025-11-25T11:20:00" },
                { "2", "Notebook", "15", "50.00", "5", "Promo", "2025-11-24T13:30:00" },
                { "5", "Ballpen", "16", "20.00", "0", "Regular", "2025-11-23T09:15:00" },
                { "4", "Bond Paper (Pack)", "7", "150.00", "0", "Regular", "2025-11-22T15:00:00" },
                { "10", "Stapler", "4", "120.00", "0", "Regular", "2025-11-21T10:30:00" },
                { "11", "Scissors", "6", "80.00", "0", "Regular", "2025-11-20T14:45:00" },
                { "6", "Protractor", "8", "25.00", "0", "Regular", "2025-11-19T11:15:00" },
                { "15", "Envelope (Pack)", "9", "45.00", "0", "Regular", "2025-11-18T13:00:00" },
                { "29", "Staples (Box)", "14", "22.00", "0", "Regular", "2025-11-17T09:45:00" },

                // ========== OCTOBER 2025 ==========
                { "17", "Compass", "7", "45.00", "0", "Regular", "2025-10-30T09:30:00" },
                { "18", "Ruler (30cm)", "12", "20.00", "0", "Regular", "2025-10-29T14:20:00" },
                { "19", "Triangle Set", "9", "35.00", "0", "Regular", "2025-10-28T10:45:00" },
                { "1", "Pencil", "18", "15.00", "0", "Regular", "2025-10-27T11:30:00" },
                { "2", "Notebook", "10", "50.00", "0", "Regular", "2025-10-26T13:15:00" },
                { "5", "Ballpen", "14", "20.00", "0", "Regular", "2025-10-25T09:45:00" },
                { "3", "Eraser", "11", "5.00", "0", "Regular", "2025-10-24T15:20:00" },
                { "4", "Bond Paper (Pack)", "6", "150.00", "0", "Regular", "2025-10-23T10:30:00" },
                { "30", "Puncher", "3", "150.00", "0", "Regular", "2025-10-22T14:00:00" },
                { "12", "Glue Stick", "8", "25.00", "0", "Regular", "2025-10-21T11:45:00" },
                { "9", "Correction Tape", "6", "40.00", "0", "Regular", "2025-10-20T13:30:00" },
                { "23", "Oil Pastel (24 colors)", "5", "180.00", "0", "Regular", "2025-10-19T10:00:00" },

                // ========== SEPTEMBER 2025 ==========
                { "1", "Pencil", "35", "15.00", "10", "Back to School", "2025-09-28T09:00:00" },
                { "2", "Notebook", "28", "50.00", "10", "Back to School", "2025-09-27T10:30:00" },
                { "5", "Ballpen", "30", "20.00", "10", "Back to School", "2025-09-26T14:15:00" },
                { "3", "Eraser", "20", "5.00", "10", "Back to School", "2025-09-25T11:45:00" },
                { "4", "Bond Paper (Pack)", "15", "150.00", "5", "Back to School", "2025-09-24T09:30:00" },
                { "6", "Protractor", "12", "25.00", "10", "Back to School", "2025-09-23T13:20:00" },
                { "7", "Permanent Marker", "18", "35.00", "10", "Back to School", "2025-09-22T10:15:00" },
                { "8", "Highlighter", "16", "30.00", "10", "Back to School", "2025-09-21T15:00:00" },
                { "13", "Colored Paper (Pack)", "10", "180.00", "5", "Back to School", "2025-09-20T11:30:00" },
                { "21", "Crayons (12 colors)", "22", "65.00", "10", "Back to School", "2025-09-19T09:45:00" },
                { "17", "Compass", "10", "45.00", "10", "Back to School", "2025-09-18T14:30:00" },
                { "18", "Ruler (30cm)", "15", "20.00", "10", "Back to School", "2025-09-17T10:00:00" },
                { "14", "Folder (Plastic)", "25", "12.00", "10", "Back to School", "2025-09-16T13:15:00" },
                { "10", "Stapler", "8", "120.00", "5", "Back to School", "2025-09-15T09:30:00" },

                // ========== AUGUST 2025 ==========
                { "1", "Pencil", "25", "15.00", "10", "Back to School", "2025-08-30T09:15:00" },
                { "2", "Notebook", "20", "50.00", "10", "Back to School", "2025-08-29T11:00:00" },
                { "5", "Ballpen", "22", "20.00", "10", "Back to School", "2025-08-28T14:45:00" },
                { "4", "Bond Paper (Pack)", "12", "150.00", "5", "Back to School", "2025-08-27T10:30:00" },
                { "20", "Calculator", "8", "250.00", "15", "Back to School", "2025-08-26T13:15:00" },
                { "10", "Stapler", "7", "120.00", "5", "Back to School", "2025-08-25T09:45:00" },
                { "11", "Scissors", "8", "80.00", "5", "Back to School", "2025-08-24T15:20:00" },
                { "6", "Protractor", "10", "25.00", "10", "Back to School", "2025-08-23T11:30:00" },
                { "19", "Triangle Set", "8", "35.00", "10", "Back to School", "2025-08-22T14:00:00" },
                { "22", "Watercolor Set", "12", "150.00", "10", "Back to School", "2025-08-21T10:15:00" },
                { "15", "Envelope (Pack)", "11", "45.00", "5", "Back to School", "2025-08-20T13:45:00" },
                { "16", "Index Cards (Pack)", "9", "55.00", "5", "Back to School", "2025-08-19T09:00:00" },

                // ========== JULY 2025 ==========
                { "23", "Oil Pastel (24 colors)", "8", "180.00", "0", "Regular", "2025-07-30T09:30:00" },
                { "24", "Sketch Pad", "10", "85.00", "0", "Regular", "2025-07-29T14:15:00" },
                { "25", "Paint Brush Set", "6", "120.00", "0", "Regular", "2025-07-28T10:45:00" },
                { "1", "Pencil", "15", "15.00", "0", "Regular", "2025-07-27T11:20:00" },
                { "2", "Notebook", "8", "50.00", "0", "Regular", "2025-07-26T13:30:00" },
                { "5", "Ballpen", "12", "20.00", "0", "Regular", "2025-07-25T09:15:00" },
                { "4", "Bond Paper (Pack)", "5", "150.00", "0", "Regular", "2025-07-24T15:00:00" },
                { "14", "Folder (Plastic)", "18", "12.00", "0", "Regular", "2025-07-23T10:30:00" },
                { "15", "Envelope (Pack)", "10", "45.00", "0", "Regular", "2025-07-22T14:45:00" },
                { "16", "Index Cards (Pack)", "7", "55.00", "0", "Regular", "2025-07-21T11:15:00" },
                { "21", "Crayons (12 colors)", "9", "65.00", "0", "Regular", "2025-07-20T13:00:00" },
                { "12", "Glue Stick", "11", "25.00", "0", "Regular", "2025-07-19T09:45:00" },

                // ========== JUNE 2025 ==========
                { "1", "Pencil", "40", "15.00", "15", "Back to School", "2025-06-28T09:00:00" },
                { "2", "Notebook", "35", "50.00", "15", "Back to School", "2025-06-27T10:30:00" },
                { "5", "Ballpen", "38", "20.00", "15", "Back to School", "2025-06-26T14:15:00" },
                { "3", "Eraser", "25", "5.00", "15", "Back to School", "2025-06-25T11:45:00" },
                { "4", "Bond Paper (Pack)", "18", "150.00", "10", "Back to School", "2025-06-24T09:30:00" },
                { "6", "Protractor", "15", "25.00", "15", "Back to School", "2025-06-23T13:20:00" },
                { "17", "Compass", "12", "45.00", "15", "Back to School", "2025-06-22T10:15:00" },
                { "18", "Ruler (30cm)", "20", "20.00", "15", "Back to School", "2025-06-21T15:00:00" },
                { "20", "Calculator", "10", "250.00", "15", "Back to School", "2025-06-20T11:30:00" },
                { "21", "Crayons (12 colors)", "28", "65.00", "15", "Back to School", "2025-06-19T09:45:00" },
                { "7", "Permanent Marker", "16", "35.00", "10", "Back to School", "2025-06-18T14:30:00" },
                { "8", "Highlighter", "18", "30.00", "10", "Back to School", "2025-06-17T10:00:00" },
                { "13", "Colored Paper (Pack)", "14", "180.00", "10", "Back to School", "2025-06-16T13:45:00" },
                { "19", "Triangle Set", "11", "35.00", "10", "Back to School", "2025-06-15T09:15:00" },

                // ========== MAY 2025 ==========
                { "26", "Binder Clips (Pack)", "8", "35.00", "0", "Regular", "2025-05-30T09:30:00" },
                { "27", "Paper Clips (Box)", "10", "28.00", "0", "Regular", "2025-05-29T14:15:00" },
                { "29", "Staples (Box)", "15", "22.00", "0", "Regular", "2025-05-28T10:45:00" },
                { "1", "Pencil", "12", "15.00", "0", "Regular", "2025-05-27T11:20:00" },
                { "2", "Notebook", "10", "50.00", "0", "Regular", "2025-05-26T13:30:00" },
                { "5", "Ballpen", "14", "20.00", "0", "Regular", "2025-05-25T09:15:00" },
                { "4", "Bond Paper (Pack)", "6", "150.00", "0", "Regular", "2025-05-24T15:00:00" },
                { "13", "Colored Paper (Pack)", "5", "180.00", "0", "Regular", "2025-05-23T10:30:00" },
                { "12", "Glue Stick", "9", "25.00", "0", "Regular", "2025-05-22T14:45:00" },
                { "9", "Correction Tape", "7", "40.00", "0", "Regular", "2025-05-21T11:15:00" },
                { "28", "Tape Dispenser", "4", "95.00", "0", "Regular", "2025-05-20T13:00:00" },
                { "30", "Puncher", "3", "150.00", "0", "Regular", "2025-05-19T09:45:00" },

                // ========== APRIL 2025 ==========
                { "1", "Pencil", "18", "15.00", "0", "Regular", "2025-04-28T09:30:00" },
                { "2", "Notebook", "12", "50.00", "5", "Promo", "2025-04-27T14:20:00" },
                { "5", "Ballpen", "15", "20.00", "0", "Regular", "2025-04-26T10:45:00" },
                { "3", "Eraser", "10", "5.00", "0", "Regular", "2025-04-25T11:30:00" },
                { "4", "Bond Paper (Pack)", "8", "150.00", "0", "Regular", "2025-04-24T13:15:00" },
                { "22", "Watercolor Set", "5", "150.00", "10", "Promo", "2025-04-23T09:45:00" },
                { "24", "Sketch Pad", "6", "85.00", "10", "Promo", "2025-04-22T15:20:00" },
                { "10", "Stapler", "4", "120.00", "0", "Regular", "2025-04-21T10:30:00" },
                { "28", "Tape Dispenser", "3", "95.00", "0", "Regular", "2025-04-20T14:00:00" },
                { "30", "Puncher", "2", "150.00", "0", "Regular", "2025-04-19T11:45:00" },
                { "11", "Scissors", "5", "80.00", "0", "Regular", "2025-04-18T13:30:00" },
                { "25", "Paint Brush Set", "4", "120.00", "5", "Promo", "2025-04-17T09:00:00" },

                // ========== MARCH 2025 ==========
                { "1", "Pencil", "22", "15.00", "0", "Regular", "2025-03-28T09:15:00" },
                { "2", "Notebook", "16", "50.00", "0", "Regular", "2025-03-27T11:00:00" },
                { "5", "Ballpen", "20", "20.00", "0", "Regular", "2025-03-26T14:45:00" },
                { "3", "Eraser", "12", "5.00", "0", "Regular", "2025-03-25T10:30:00" },
                { "4", "Bond Paper (Pack)", "10", "150.00", "0", "Regular", "2025-03-24T13:15:00" },
                { "6", "Protractor", "8", "25.00", "0", "Regular", "2025-03-23T09:45:00" },
                { "17", "Compass", "6", "45.00", "0", "Regular", "2025-03-22T15:20:00" },
                { "18", "Ruler (30cm)", "10", "20.00", "0", "Regular", "2025-03-21T10:30:00" },
                { "7", "Permanent Marker", "9", "35.00", "0", "Regular", "2025-03-20T14:00:00" },
                { "8", "Highlighter", "11", "30.00", "0", "Regular", "2025-03-19T11:45:00" },
                { "14", "Folder (Plastic)", "15", "12.00", "0", "Regular", "2025-03-18T13:30:00" },
                { "26", "Binder Clips (Pack)", "7", "35.00", "0", "Regular", "2025-03-17T09:00:00" }
        };

        /**
         * Returns the dummy product data array.
         *
         * @return 2D array of product data strings
         */
        public static String[][] getDummyProduct() {
                return dummyProduct;
        }

        /**
         * Returns the dummy sales data array.
         *
         * @return 2D array of sales data strings
         */
        public static String[][] getDummySales() {
                return dummySales;
        }

}