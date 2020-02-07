package com.thoughtworks;

import java.util.Arrays;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  /**
   * 接收用户选择的菜品和数量，返回计算后的汇总信息
   *
   * @param selectedItems 选择的菜品信息
   */
  public static String bestCharge(String selectedItems) {
    // 此处补全代码
    String[] itemIds = getItemIds();
    double[] itemPrices = getItemPrices();
    int[] selectedItemsIndex = getSelectedItemsIndex(itemIds, selectedItems);
    int[] selectedItemsNum = getSelectedItemNum(selectedItems);
    double totalPrice = getTotalPrice(selectedItemsIndex, selectedItemsNum, itemPrices);
    double overDescTotalPrice = getOverDescTotalPrice(totalPrice);
    double halfTotalPrice = getHalfTotalPrice(selectedItemsIndex, selectedItemsNum, itemPrices, itemIds);
    selectedItems = getBasicInfo(selectedItemsIndex, selectedItemsNum);
    String halfItemsName = getHalfItemsName(selectedItemsIndex, itemIds);
    selectedItems += chooseDiscountWay(totalPrice, overDescTotalPrice, halfTotalPrice, halfItemsName);
    return selectedItems;
  }

  public static int[] getSelectedItemsIndex(String[] itemIds, String selectedItems) {
    String[] selectedItemString = selectedItems.split(",");
    int index = 0;
    int[] selectedItemsIndex = new int[selectedItemString.length];
    for(int i = 0; i < itemIds.length; i++) {
      if(selectedItems.indexOf(itemIds[i]) != -1) {
        selectedItemsIndex[index++] = i;
      }
    }
    return selectedItemsIndex;
  }

  public static int[] getSelectedItemNum(String selectedItems) {
    String[] selectedItemString = selectedItems.split(",");
    int[] selectedItemsNum = new int[selectedItemString.length];
    for(int i = 0; i < selectedItemString.length; i++) {
      selectedItemsNum[i] = Integer.parseInt(selectedItemString[i].split("x")[1].trim());
    }
    return selectedItemsNum;
  }

  public static double getTotalPrice(int[] selectedItemsIndex, int[] selectedItemsNum, double[] itemPrices) {
    int totalPrice = 0;
    for(int i = 0; i < selectedItemsIndex.length; i++) {
      totalPrice += selectedItemsNum[i] * itemPrices[selectedItemsIndex[i]];
    }
    return totalPrice;
  }

  public static double getOverDescTotalPrice(double totalPrice) {
    if(totalPrice >= 30) {
      totalPrice -= 6;
    }
    return totalPrice;
  }

  public static double getHalfTotalPrice(int[] selectedItemsIndex, int[] selectedItemsNum, double[] itemPrices, String[] itemIds) {
    String[] halfPriceIds = getHalfPriceIds();
    int totalPrice = 0;
    for(int i = 0; i < selectedItemsIndex.length; i++) {
      if(Arrays.asList(halfPriceIds).contains(itemIds[selectedItemsIndex[i]]) ) {
        totalPrice += selectedItemsNum[i] * itemPrices[selectedItemsIndex[i]] / 2;
      } else {
        totalPrice += selectedItemsNum[i] * itemPrices[selectedItemsIndex[i]];
      }
    }
    return totalPrice;
  }

  public static String getBasicInfo(int[] selectedItemsIndex, int[] selectedItemsNum) {
    String[] itemNames = getItemNames();
    double[] itemPrices = getItemPrices();
    String basicInfo = "============= 订餐明细 =============\n";
    for (int i = 0; i < selectedItemsIndex.length; i++) {
      int index = selectedItemsIndex[i];
      basicInfo += itemNames[index] + " x " + selectedItemsNum[i] + " = " + (int)(selectedItemsNum[i] * itemPrices[index]) + "元\n";
    }
    return basicInfo;
  }

  public static String getHalfItemsName(int[] selectedItemsIndex, String[] itemIds) {
    String[] itemNames = getItemNames();
    String[] halfPriceIds = getHalfPriceIds();
    String halfItemsName = "";
    for(int i = 0; i < selectedItemsIndex.length; i++) {
      if(Arrays.asList(halfPriceIds).contains(itemIds[selectedItemsIndex[i]]) ) {
        if(i == selectedItemsIndex.length - 1) {
          halfItemsName += itemNames[i];
        } else {
          halfItemsName = halfItemsName + itemNames[i] + "，";
        }
      }
    }
    return halfItemsName;
  }

  public static String chooseDiscountWay(double totalPrice, double overDescTotalPrice, double halfTotalPrice ,String halfItemsName) {
    String discountWay = "";
    if(overDescTotalPrice <= halfTotalPrice) {
      if(overDescTotalPrice == totalPrice) {
        discountWay = "";
      } else {
        discountWay = "-----------------------------------\n"
                + "使用优惠:\n"
                + "满30减6元，省6元\n";
      }
    } else {
      discountWay = "-----------------------------------\n"
              + "使用优惠:\n"
              + "指定菜品半价(" + halfItemsName + ")，省" + (int)(totalPrice - halfTotalPrice) + "元\n";
    }
    String printTotalPrice = "-----------------------------------\n"
            + "总计：" + (int)(overDescTotalPrice <= halfTotalPrice ? overDescTotalPrice : halfTotalPrice) + "元\n"
            + "===================================";
    return discountWay + printTotalPrice;
  }

  /**
   * 获取每个菜品依次的编号
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }

  /**
   * 获取每个菜品依次的名称
   */
  public static String[] getItemNames() {
    return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
  }

  /**
   * 获取每个菜品依次的价格
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * 获取半价菜品的编号
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }
}
