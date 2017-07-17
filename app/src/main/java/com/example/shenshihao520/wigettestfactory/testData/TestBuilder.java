package com.example.shenshihao520.wigettestfactory.testData;

/**
 * 尝试Builder设计模式
 * Created by shenshihao520 on 2017/7/7.
 */

//使用          TestBuilder testBuilder = new TestBuilder.Builder().address("shen").name("asd").age(12).build();

public class TestBuilder {
    String name ;
    int age;
    String address;

    private TestBuilder(Builder builder) {
        name = builder.name;
        age = builder.age;
        address = builder.address;
    }

    public static final class Builder {
        private String name;
        private int age;
        private String address;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder age(int val) {
            age = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

        public TestBuilder build() {
            return new TestBuilder(this);
        }
    }
}
