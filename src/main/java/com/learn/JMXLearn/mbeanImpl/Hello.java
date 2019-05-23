package com.learn.JMXLearn.mbeanImpl;

import com.learn.JMXLearn.mbeanInterface.HelloMBean;

public class Hello implements HelloMBean {
        private String name;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String printHello() {
            return "Hello "+ name;
        }

        @Override
        public String printHello(String whoName) {
            return "Hello  " + whoName;
        }
}
