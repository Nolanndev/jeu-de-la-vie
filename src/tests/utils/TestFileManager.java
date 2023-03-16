// package tests.utils;

// import java.util.HashMap;

// import main.utils.ProfileManager;

// public class TestFileManager {
//     private static HashMap<String,String> map;
//     private static HashMap<String,String> map1;

//     public static void startTest() {
//         testParseLine();
//     }

//     private static void testParseLine() {
//         map = new HashMap<>();
//         for (int i=0; i<5; i++) {
//             map.put("key" + i, "value" + i);
//         }
//         ProfileManager.write(map, "test");
//         map1 = ProfileManager.parse("test");
//         assert map.equals(map1) : "Les données entrées ne sont pas les mêmes que les données sorties";


//     }

// }
