package com.nextun.enums;

public enum EnumAjax {

  SUCCESS(0, "success"), EXCEPTION(-1, "systemerror"), DATAFORMATERROR(-2,
      "dataerror"), ERROR(-3, "error"), EMPTY(-4, "empty"), NOTLOGIN(-5,
      "notlogin");

  private int code;
  private String msg;

  private EnumAjax(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return this.code;
  }

  public String getMsg() {
    return this.msg;
  }
}
