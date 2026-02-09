package utilities;

public class DummyData {

        // Format: {Name, Category, Supplier, Price, Cost, Stock, ReorderLevel}
        static String[][] dummyProduct = {
                        { "Pencil", "pen", "Aali", "14.40", "10.50", "100", "20" },
                        { "Red Ballpen", "pen", "Aali", "16.20", "14.20", "50", "20" },
                        { "Blue Ballpen", "pen", "Aali", "16.20", "14.20", "75", "20" },
                        // {"Black Ballpen", "pen", "Aali", "16.20", "14.20", "80", "20"},
                        // {"Highlighter Yellow", "pen", "Aali", "25.00", "18.00", "30", "10"},
                        // { "Highlighter Pink", "pen", "Aali", "25.00", "18.00", "25", "10" },
                        // { "Pantasa", "utilities", "Jiana", "9.50", "6.10", "40", "10" },
                        // { "Eraser", "utilities", "Jiana", "5.00", "3.00", "60", "15" },
                        // {"Sharpener", "utilities", "Jiana", "8.50", "5.50", "45", "15"},
                        // {"Ruler 12inch", "utilities", "Jiana", "15.00", "10.00", "35", "10"},
                        // {"Scissors", "utilities", "Jiana", "35.00", "25.00", "20", "8"},
                        // {"Glue Stick", "utilities", "Jiana", "20.00", "12.00", "40", "12"},
                        // {"A4", "paper", "JR", "3.50", "1.50", "100", "20"},
                        { "Short Bond Paper", "paper", "JR", "2.20", "2.00", "100", "20" },
                        // { "Long Bond Paper", "paper", "JR", "2.50", "2.20", "80", "20" },
                        // {"Yellow Pad", "paper", "JR", "25.00", "18.00", "50", "15"},
                        // {"Notebook 80 Leaves", "notebook", "BookWorld", "45.00", "32.00", "60",
                        // "15"},
                        // {"Notebook 100 Leaves", "notebook", "BookWorld", "55.00", "40.00", "45",
                        // "15"},
                        // {"Spiral Notebook", "notebook", "BookWorld", "65.00", "48.00", "40", "12"},
                        // {"Index Cards", "paper", "JR", "30.00", "20.00", "35", "10"},
        };

        // Format: {productId, productName, quantitySold, unitPrice, discountPercentage,
        // seasonTag, dateTime}
        static String[][] dummySales = {
                        // Recent sales (today and yesterday)
                        { "1", "Pencil", "5", "14.40", "0", "Regular", "2026-02-02T09:15:30" },
                        { "2", "Red Ballpen", "3", "16.20", "0", "Regular", "2026-02-02T09:30:45" },
                        { "13", "A4", "10", "3.50", "0", "Regular", "2026-02-02T10:00:00" },
                        { "17", "Notebook 80 Leaves", "2", "45.00", "10", "Back to School", "2026-02-02T11:20:15" },
                        // { "4", "Black Ballpen", "8", "16.20", "0", "Regular", "2026-02-02T13:45:00"
                        // },
                        // { "8", "Eraser", "4", "5.00", "0", "Regular", "2026-02-02T14:10:30" },
                        // { "14", "Short Bond Paper", "15", "2.20", "0", "Regular",
                        // "2026-02-02T15:30:00" },

                        // Yesterday's sales
                        { "1", "Pencil", "10", "14.40", "0", "Regular", "2026-02-01T08:30:00" },
                        { "3", "Blue Ballpen", "6", "16.20", "0", "Regular", "2026-02-01T09:45:20" },
                        { "18", "Notebook 100 Leaves", "3", "55.00", "10", "Back to School", "2026-02-01T10:15:00" },
                        { "16", "Yellow Pad", "5", "25.00", "0", "Regular", "2026-02-01T11:00:45" },
                        // { "11", "Scissors", "2", "35.00", "0", "Regular", "2026-02-01T12:30:00" },
                        // { "7", "Pantasa", "7", "9.50", "0", "Regular", "2026-02-01T14:20:15" },

                        // Last week sales
                        // { "5", "Highlighter Yellow", "4", "25.00", "0", "Regular",
                        // "2026-01-30T10:00:00" },
                        // { "17", "Notebook 80 Leaves", "5", "45.00", "15", "Back to School",
                        // "2026-01-30T11:30:00" },
                        // { "1", "Pencil", "12", "14.40", "0", "Regular", "2026-01-29T09:00:00" },
                        // { "13", "A4", "20", "3.50", "0", "Regular", "2026-01-29T10:30:00" },
                        { "14", "Short Bond Paper", "18", "2.20", "0", "Regular", "2026-01-28T13:15:00" },
                        { "19", "Spiral Notebook", "4", "65.00", "15", "Back to School", "2026-01-28T14:00:00" },
                        { "2", "Red Ballpen", "9", "16.20", "0", "Regular", "2026-01-27T11:45:00" },

                        // Last month sales
                        // { "1", "Pencil", "15", "14.40", "5", "New Year Sale", "2026-01-15T10:00:00"
                        // },
                        // { "4", "Black Ballpen", "10", "16.20", "5", "New Year Sale",
                        // "2026-01-15T11:30:00" },
                        // { "17", "Notebook 80 Leaves", "8", "45.00", "20", "Back to School",
                        // "2026-01-14T09:00:00" },
                        // { "18", "Notebook 100 Leaves", "6", "55.00", "20", "Back to School",
                        // "2026-01-14T10:15:00" },
                        // { "13", "A4", "25", "3.50", "0", "Regular", "2026-01-12T13:00:00" },
                        // { "16", "Yellow Pad", "7", "25.00", "0", "Regular", "2026-01-10T14:30:00" },
                        { "12", "Glue Stick", "5", "20.00", "0", "Regular", "2026-01-09T10:45:00" },
                        { "9", "Sharpener", "6", "8.50", "0", "Regular", "2026-01-08T11:20:00" },
                        { "8", "Eraser", "10", "5.00", "0", "Regular", "2026-01-07T12:00:00" },
                        { "20", "Index Cards", "4", "30.00", "0", "Regular", "2026-01-05T15:00:00" },
        };

        public static String[][] getDummyProduct() {
                return dummyProduct;
        }

        public static String[][] getDummySales() {
                return dummySales;
        }

}