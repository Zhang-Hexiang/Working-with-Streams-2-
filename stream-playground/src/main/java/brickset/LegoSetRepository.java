package brickset;

import repository.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }

    public static void main(String[] args) {


        LegoSetRepository legoSetRepository = new LegoSetRepository();
        System.out.println(legoSetRepository.anyLegoSetHaveMoreThan2000Pieces()); // Q1
        System.out.println(legoSetRepository.getAllLegoSetsNames()); // Q2
        System.out.println(legoSetRepository.whichLegoSetHavetheBiggestPiecesNumber()); // Q3
        System.out.println(legoSetRepository.getLegoSetsNumberAndName()); // Q4
        System.out.println(legoSetRepository.classifyLegoSetsByPackingType()); // Q5
    }


    /**Q1
     * return the boolean value TRUE if there is any Lego set contains more than 2000 pieces, return FALSE if not
     * use the .anyMatch() method
     * @return boolean TRUE if there is any Lego set contains more than 2000 pieces, return FALSE if not
     */
    public boolean anyLegoSetHaveMoreThan2000Pieces(){
        return getAll().stream()
                .anyMatch(legoSet ->  legoSet.getPieces() > 2000);
    }


    /** Q2
     * .flatmap()
     * return all Lego sets' name by using the flatMap()
     * @return List<String> all Lego sets' name
     */
    public List<String> getAllLegoSetsNames(){
        return getAll().stream()
                .map( str-> str.getName().split(" , ") )
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

    }



    /** Q3
     * use the .reduce() method
     * find out which LegoSet have the biggest pieces number
     * @return String the Lego set details for which have the most pieces
     */
    public String whichLegoSetHavetheBiggestPiecesNumber(){
        return getAll().stream()
                .reduce((ele1,ele2) -> {
                    return ele1.getPieces() > ele2.getPieces() ? ele1:ele2;})
                .get()
                .toString();
    }


    /** Q4
     * return the Lego sets' number and name by passing a collector obtained form Collectors.toMap() to collect()
     * @return Map<String,String> return the Lego sets' number and name
     */
    public Map<String,String> getLegoSetsNumberAndName(){
        return getAll().stream()
                .collect(Collectors.toMap(LegoSet::getNumber,LegoSet::getName));
    }


    /** Q5
     *  Use a pipeline which contains one downstream collector return all Lego sets classified by PackingType
     * @return Map<PackagingType, Optional<LegoSet>> return all Lego sets classified by PackingType
     */
    public Map<PackagingType, Optional<LegoSet>> classifyLegoSetsByPackingType(){

        return getAll().stream()
                .collect(Collectors.groupingBy(
                        LegoSet::getPackagingType,
                        Collectors.maxBy(Comparator.comparing(LegoSet::getPieces))));
    }
}


