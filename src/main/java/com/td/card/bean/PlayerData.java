package com.td.card.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@Component
//@ConfigurationProperties(prefix = "playerdata")
//@PropertySource(value = "application.yml", encoding = "UTF-8")
public class PlayerData {


    private int HP = 2000;

    private int money = 4000;

    private int efficiency = 500;

    private int upEfficiency = 500;

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getUpEfficiency() {
        return upEfficiency;
    }

    public void setUpEfficiency(int upEfficiency) {
        this.upEfficiency = upEfficiency;
    }
}
