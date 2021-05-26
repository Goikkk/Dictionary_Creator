import java.io.*;
import java.util.*;
import static java.lang.System.exit;

/**
 * Creating a dictionary from provided files with plane text.
 *
 * @author Rafal Karwowski
 */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in); // Scanner to read input from user

        int size; // How many files should dictionary consist

        String anotherDictionary; // Answer to the question: does user want to create another dictionary?

        do
        {

            size = sizeOfDictionary(sc); // Calling method which asks user for size of a dictionary


            String[] data = new String[size]; // Data, from files, are stored here
            HashMap<String, Integer> map = new HashMap<>(); // Data structure for dictionary


            System.out.print("\nReading data from files: ");
            long startTime = System.currentTimeMillis(); // Time before completing task
                readData(data); // Calling method to read data from files
            long stopTime = System.currentTimeMillis(); // Time after completing task
            System.out.println((stopTime-startTime) + " milliseconds.");


            System.out.print("Counting words: ");
            startTime = System.currentTimeMillis();
                countWords(map, data); // Calling method to count words
            stopTime = System.currentTimeMillis();
            System.out.println((stopTime-startTime) + " milliseconds.");


            System.out.print("Sorting: ");
            startTime = System.currentTimeMillis();
                Map<String, Integer> mapSorted = sortByValue(map); // Calling method to sort dictionary
            stopTime = System.currentTimeMillis();
            System.out.println((stopTime-startTime) + " milliseconds.");


            System.out.print("Creating HTML file: ");
            startTime = System.currentTimeMillis();
                htmlOutput(mapSorted, size); // Calling method to create HTML file and put dictionary there
            stopTime = System.currentTimeMillis();
            System.out.println((stopTime-startTime) + " milliseconds.");


            //  Text search in dictionary.

            String checkWord; // Word which user want to check
            checkWord = sc.nextLine(); // It is only protection from first input
            do
            {
                System.out.print("\nDo you want to find a word? (y/n) ");
                checkWord = sc.nextLine();

                if(checkWord.contains("y"))
                {
                    System.out.print("Enter the word: ");
                    String word = sc.nextLine();
                    System.out.println("Number of occurrence: " + map.get(word));
                }
            }while(checkWord.contains("y"));


            System.out.print("\nDo you want to create another dictionary? (y/n) ");
            anotherDictionary = sc.nextLine();

        }while(anotherDictionary.contains("y"));


    }


    /**
     * Method asks user of the size of dictionary.
     *
     * @param sc Scanner for input
     * @return size of dictionary
     */

    public static int sizeOfDictionary(Scanner sc)
    {
        System.out.println("\nHow big dictionary do you want to create?");
        System.out.println("1. 100 files.");
        System.out.println("2. 1000 files.");
        System.out.println("3. 7600 files.");

        int size = 1;

        boolean correctChoose = false; // Is input correct
        do
        {
            try{
                System.out.print("\nSelect 1, 2 or 3: ");
                size = sc.nextInt();
                if(size == 1)
                {
                    size = 100;
                    correctChoose = true;
                }else if(size == 2)
                {
                    size = 1000;
                    correctChoose = true;
                }else if(size == 3)
                {
                    size = 7600;
                    correctChoose = true;
                }else
                    System.out.println("Wrong nr!");

            }catch (Exception e){
                System.out.println("Wrong input. Try again.");
                sc.next();
            }
        }while(!correctChoose);

        return size;

    }


    /**
     * Method reads data from txt files and returns them in String array.
     *
     * @param data String array for data
     */

    public static void readData(String[] data)
    {

        for(int i = 1; i <= data.length; i++)
        {

            try{
                File myObj = new File("PTDataAng/" + i + ".txt");
                Scanner myReader = new Scanner(myObj);
                while(myReader.hasNextLine()){
                    data[i-1] = myReader.nextLine();
                }
                myReader.close();
            }catch(FileNotFoundException e){
                System.out.println("An error occured!");
                e.printStackTrace();
                exit(0);
            }
        }

    }


    /**
     * Method reads data from one txt file and returns them in String array.
     *
     * @param data String array for data
     */

    public static void readDataFromOneFile(String[] data)
    {

        int i = 0;

        try{
            File myObj = new File("onefile.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                data[i] = myReader.nextLine();
                i++;
            }
            myReader.close();
        }catch(FileNotFoundException e){
            System.out.println("An error occured!");
            e.printStackTrace();
            exit(0);
        }


    }


    /**
     * Method counts number of occurrence of words in String array.
     *
     * @param hm HashMap for storing data
     * @param data data which are going to be count
     */

    public static void countWords(HashMap<String, Integer> hm, String[] data)
    {

        for (String datum : data) {

            int newWordStart = 0; // Index on which next word starts

            for (int j = 0; j < datum.length(); j++) {
                if (datum.charAt(j) == ' ') {
                    String newWord = datum.substring(newWordStart, j);

                    int count = hm.getOrDefault(newWord, 0);
                    hm.put(newWord, count + 1);

                    newWordStart = j + 1;
                }
            }

            int countLastWord = hm.getOrDefault(datum.substring(newWordStart), 0);
            hm.put(datum.substring(newWordStart), countLastWord + 1);

        }

    }


    /**
     * Method sorts words from dictionary according their occurrence.
     *
     * @param hm map
     * @return sorted map
     */

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());


        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >()
        {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        

        HashMap<String, Integer> temp = new LinkedHashMap<>(); // Put data from sorted list to hashmap
        for (Map.Entry<String, Integer> insert : list) {
            temp.put(insert.getKey(), insert.getValue());
        }
        return temp;
    }


    /**
     * Method sorts words from dictionary according their occurrence using bubble sort.
     *
     * @param hm map
     * @return sorted map
     */

    public static HashMap<String, Integer> sortBubble(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());


        Map.Entry<String, Integer> temp2;
        // Sort the list
        for (int x=0; x<list.size(); x++)
        {
            for(int i = 0; i < list.size() - x - 1; i++)
            {
                if(list.get(i).getValue() < (list.get(i+1).getValue()))
                {
                    temp2 = list.get(i);
                    list.set(i,list.get(i+1) );
                    list.set(i+1, temp2);
                }
            }
        }

        HashMap<String, Integer> temp = new LinkedHashMap<>(); // Put data from sorted list to hashmap
        for (Map.Entry<String, Integer> insert : list) {
            temp.put(insert.getKey(), insert.getValue());
        }
        return temp;
    }


    /**
     * Method creates html file with words and number of occurrence from dictionary.
     *
     * @param m map
     * @param size number of documents used to create dictionary
     */

    public static void htmlOutput(Map<String, Integer> m, int size)
    {

        File f = new File("Dictionary_"+size+"_files.html"); // Creating new file

        StringBuilder html = // Beginning of HTML file
                new StringBuilder("<head><style>table, th, td {" +
                        "  border: 1px solid black;" +
                        "}" +
                        "</style></head>" +
                        "<body>" +
                        "<h1>Dictionary created from " + size + " files!</h1>" +
                        "<table>" +
                        "<tr>" +
                        "<th>Word</th>" +
                        "<th>Occurrence</th>" +
                        "</tr>");


        for (Map.Entry<String, Integer> en : m.entrySet()) { // Data from dictionary put to a HTML table
            html.append("<tr>" + "<th>").append(en.getKey()).append("</th>");
            html.append("<th>").append(en.getValue()).append("</th>").append("</tr>");
        }

        html.append("</table>" + "</body>"); // End of the file

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html.toString()); // Writing data to HTML file
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
