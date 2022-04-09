package main.biggreenbook.entity.vo;

public class Example {
    private String sort;
    private String search;
    private int amount;
    private String follower;

    public Example() {
    }

    public Example(String sort, String search, int amount, String follower) {
        this.sort = sort;
        this.search = search;
        this.amount = amount;
        this.follower = follower;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }
}
