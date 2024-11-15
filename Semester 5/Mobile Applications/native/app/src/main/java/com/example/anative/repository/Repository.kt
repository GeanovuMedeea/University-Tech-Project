package com.example.anative.repository

import com.example.anative.domain.Item

class Repository {
    private var nextId = 25
    private var itemList = mutableListOf<Item>(
        Item(1, "Item 1", "A tale of a brave knight who defeated a dragon.", 1200, "England", "knight,dragon,bravery", "https://www.youtube.com/1"),
        Item(2, "Item 2", "A story of a magical forest where creatures roam freely.", 1500, "Germany", "forest,magic,creatures", "https://www.youtube.com/2"),
        Item(3, "Item 3", "The legend of a lost city of gold that was never found.", 1300, "South America", "city of gold,treasure,explorers", "https://www.youtube.com/3"),
        Item(4, "Item 4", "A myth about the origins of the moon and stars.", -1000, "Greece", "moon,stars,creation", "https://www.youtube.com/4"),
        Item(5, "Item 5", "A hero who ventured into the underworld to save his beloved.", -900, "Greece", "underworld,hero,love", "https://www.youtube.com/5"),
        Item(6, "Item 6", "The fable of a fox who tricked a lion to escape danger.", 800, "Africa", "fox,lion,trickery", "https://www.youtube.com/6"),
        Item(7, "Item 7", "A myth about a sea god who controlled the tides.", -1100, "Greece", "sea god,tides,ocean", "https://www.youtube.com/7"),
        Item(8, "Item 8", "A story of a warrior who fought to protect his village.", 1400, "China", "warrior,village,protection", "https://www.youtube.com/8"),
        Item(9, "Item 9", "The tale of a cursed treasure hidden beneath a mountain.", 1600, "Egypt", "treasure,curse,mountain", "https://www.youtube.com/9"),
        Item(10, "Item 10", "A story of an ancient prophecy that foretold the fall of a kingdom.", 1700, "Rome", "prophecy,kingdom,downfall", "https://www.youtube.com/10"),
        Item(11, "Item 11", "A legend about a powerful sorceress who could control the weather.", 1200, "Scotland", "sorceress,weather,power", "https://www.youtube.com/11"),
        Item(12, "Item 12", "The myth of a great flood that covered the entire world.", -2000, "Mesopotamia", "flood,survival,world", "https://www.youtube.com/12"),
        Item(13, "Item 13", "A story of a warrior who became a god after his death.", 1300, "Japan", "warrior,god,afterlife", "https://www.youtube.com/13"),
        Item(14, "Item 14", "The tale of a hidden treasure guarded by a giant bird.", -1500, "Middle East", "treasure,bird,guardian", "https://www.youtube.com/14"),
        Item(15, "Item 15", "A fable about a tortoise who outsmarted a hare in a race.", 600, "Asia", "tortoise,hare,race", "https://www.youtube.com/15"),
        Item(16, "Item 16", "The myth of a brave queen who led her army into battle.", -1600, "Egypt", "queen,battle,army", "https://www.youtube.com/16"),
        Item(17, "Item 17", "A story of a prince who searched for a mythical flower.", 1200, "India", "prince,flower,mythical", "https://www.youtube.com/17"),
        Item(18, "Item 18", "The legend of a king who turned into a lion to protect his kingdom.", 1400, "Africa", "king,lion,protection", "https://www.youtube.com/18"),
        Item(19, "Item 19", "A story about a woman who can transform into a swan.", 1000, "Europe", "swan,transformation,woman", "https://www.youtube.com/19"),
        Item(20, "Item 20", "The tale of a lost prince raised by wolves in the forest.", 800, "Russia", "prince,wolves,forest", "https://www.youtube.com/20"),
        Item(21, "Item 21", "A myth about a hero who must retrieve a magical artifact to save his village.", 1800, "France","hero,artifact,village", "https://www.youtube.com/21"),
        Item(22, "Item 22", "The legend of an immortal warrior who fought for justice.", -1100, "India", "immortal,warrior,justice", "https://www.youtube.com/22"),
        Item(23, "Item 23", "A story of a magical creature that grants wishes to the pure of heart.", 1300, "China", "creature,wishes,magic", "https://www.youtube.com/23"),
        Item(24, "Item 24", "A tale of a fallen angel who seeks redemption.", 1200, "Europe", "angel,fall,redemption", "https://www.youtube.com/24"),
        Item(25, "Item 25", "The myth of a great tree that grants eternal life.", -1500, "Africa", "tree,eternal life,nature", "https://www.youtube.com/25")
    )

    fun getItems():List<Item>{
        return itemList
    }

    fun addItem(item:Item){
        itemList.add(item)
    }

    fun deleteItem(itemId: Int) {
        val itemToRemove = itemList.find { it.id == itemId }
        if (itemToRemove != null) {
            itemList.remove(itemToRemove)
        }
    }

    fun updateItem(item: Item) {
        val itemToUpdate = itemList.find { it.id == item.id }

        if (itemToUpdate != null) {
            itemToUpdate.name = item.name
            itemToUpdate.origin = item.origin
            itemToUpdate.year = item.year
            itemToUpdate.keywords = item.keywords
            itemToUpdate.description = item.description
            itemToUpdate.youtubeLink = item.youtubeLink
        }
    }

    fun getItemById(itemId: Int): Item? {
        val foundItem = itemList.find { it.id == itemId }
        if (foundItem != null) {
            return foundItem
        }
        return null
    }

    fun getItemsCount(): Int {
        return itemList.size
    }

    fun getNextId(): Int {
        nextId+=1
        return nextId
    }
}