package com.example.assistant104.dao;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TranslateDao {

	public static ArrayList<String> msgCut(String msg) {
		if (msg.toString().trim().replaceAll("[\b\r\n\t]*", "").length() % 2 == 0) {
			ArrayList<String> msgCut = new ArrayList<String>();
			int i = 0;
			for (i = 0; i < msg.trim().length() - 1; i++) {
				if (i % 2 == 0) {
					msgCut.add(msg.toString().trim().replaceAll("\n", "").substring(i, i + 2));
				}
			}
			return msgCut;
		}
		return null;
	}

	public static void msgSet(String msg, EditText result, Context context) {
		ArrayList<String> msgList = msgCut(msg);
		StringBuilder anaMsg = new StringBuilder();
		int flag = 0;
		if (msgList != null) {
			int i = 0;
			String reasonOfTp = "";
			String sq;
			flag = 0;
			for (i = 0; i < msgList.size() && flag == 0; i++) {
				switch (i) {
				case 0:
					if (msgList.get(i).equals("68")) {
						anaMsg.append("启动符：" + msgList.get(i) + "\n");
					} else {
						anaMsg.replace(0, anaMsg.length(), "报头不正确");
						break;
					}
					break;
				case 1:
					int msgLength = Integer.parseInt(msgList.get(i), 16);
					String theContext = "";
					try {
						for (int j = 0; j < msgLength; j++) {
							theContext = theContext + msgList.get(j + 2) + " ";
						}
						anaMsg.append("长度：" + msgList.get(i));
						anaMsg.append("(" + msgLength + "个字节，即：" + theContext
								+ ")\n");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						flag = 1;
						e.printStackTrace();
						anaMsg.replace(0, anaMsg.length(), "数据报文不完整");
					}
					break;

				case 2:
					try {
						anaMsg.append("控制域：" + msgList.get(i));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					anaMsg.append(msgList.get(i));
					break;
				case 4:
					anaMsg.append(msgList.get(i));
					break;
				case 5:
					anaMsg.append(msgList.get(i) + "\n");
					String x1 = String.format("%08d", (hexToBinary(msgList.get(2))));
					String x3 = String.format("%08d", (hexToBinary(msgList.get(4))));
					if (x1.substring(7, 8).equals("0") &&
							x3.substring(7, 8).equals("0")) {
						anaMsg.append("I帧：(发送序列号N(S)LSB："+msgList.get(2)+
								",MSB发送序列号N(S)："+msgList.get(3)
								+",接收序列号N(R)LSB："+msgList.get(4)
								+",MSB接收序列号N(R)："+msgList.get(5)+")");
					}else if(x1.substring(6, 8).equals("01") &&
							x3.substring(7, 8).equals("0")){
						anaMsg.append("S帧：(控制域1："+msgList.get(2)+
								",控制域2："+msgList.get(3)
								+",接收序列号N(R)LSB："+msgList.get(4)
								+",MSB接收序列号N(R)："+msgList.get(5)+")");
					}else if(x1.substring(6,8).equals("11") &&
							x3.substring(7,8).equals("0")){
						anaMsg.append("U帧：(控制域1：(TESTER:"+x1.substring(0, 2)+
								",STOPDT:"+x1.substring(2, 4)+
								",STARTDT:"+x1.substring(4, 6)+
								"),控制域2："+msgList.get(3)
								+",控制域3："+msgList.get(4)
								+",控制域4："+msgList.get(5)+")");
					}
					anaMsg.append("\n");
					break;
				case 6:
					String type = msgToType(msgList.get(i));
					anaMsg.append("类型标识：" + msgList.get(i) + "(" + type + ")"
							+ "\n");
					break;
				case 7:
					int wordChange = Integer.parseInt(msgList.get(i), 16);
					String firstOfA = "0";
					sq = "非数序";
					if (wordChange >= 128) {
						wordChange -= 128;
						firstOfA = "1";
						sq = "顺序";
					}
					anaMsg.append("可变结构限定词：" + msgList.get(i) + "," + sq
							+ "(SQ=" + firstOfA + ",number = " + wordChange
							+ "),即：有" + wordChange + "个遥测信息\n");
				case 8:
					reasonOfTp = TpReason(msgList.get(8));
					break;
				case 9:
					anaMsg.append("传送原因：" + msgList.get(8) + msgList.get(9)
							+ "," + reasonOfTp + "\n");
					break;
				case 10:
					anaMsg.append("公共地址：" + msgList.get(10) + msgList.get(11));
					break;
				case 11:
					anaMsg.append("(低前高后)\n");
					break;
				default:
					break;
				}
			}
			if (msgList.size() > 6) {

				if (msgList.get(6).equals("64") || msgList.get(6).equals("2d")
						|| msgList.get(6).equals("2D") || msgList.get(6).equals("2e")
						|| msgList.get(6).equals("2E")&& flag != 1) {

					try {
						anaMsg.append("信息体地址：" + msgList.get(12)
								+ msgList.get(13) + msgList.get(14) + "\n");

					} catch (IndexOutOfBoundsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						anaMsg.replace(0, anaMsg.length(), "数据报文不完整！\n");
						return;
					}
					try {
						anaMsg.append("信息体元素：" + msgList.get(15) + "\n");
					} catch (IndexOutOfBoundsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						anaMsg.append("信息体元素：" + 00 + "\n");
					}

				} else if (msgList.get(6).equals("01")
						|| msgList.get(6).equals("03") && flag != 1) {
					int a;
					try {
						a = Integer.parseInt(msgList.get(7), 16);
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						Toast.makeText(context, "请确认输入的十六进制数据", 0).show();
						return;
					}

					if (a >= 128) {
						String b1;
						try {
							b1 = msgList.get(12);
						} catch (IndexOutOfBoundsException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							b1 = "00";
						}
						String b2;
						try {
							b2 = msgList.get(13);
						} catch (IndexOutOfBoundsException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							b2 = "00";
						}
						String b3;
						try {
							b3 = msgList.get(14);
						} catch (IndexOutOfBoundsException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							b3 = "00";
						}
						anaMsg.append("信息体地址：" + b1 + b2 + b3 + "\n");
						for (int j = 1; j <= a - 128; j++) {
							try {
								anaMsg.append("数据" + j + "："
										+ msgList.get(j + 13) + "\n");
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								anaMsg.append("数据" + j + "：00\n");
								e.printStackTrace();
							}
						}
					} else {
						int c = 1;
						for (int j = 1; j <= a; j++) {
							String a1;
							try {
								a1 = msgList.get(c + 11);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a1 = "00";
							}
							String a2;
							try {
								a2 = msgList.get(c + 12);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a2 = "00";
							}
							String a3;
							try {
								a3 = msgList.get(c + 13);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a3 = "00";
							}
							String a4;
							try {
								a4 = msgList.get(c + 14);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a4 = "00";
							}
							anaMsg.append("第" + j + "个信息体地址：" + a1 + a2 + a3
									+ "\n");
							anaMsg.append("第" + j + "个信息体元素：" + a4 + "\n");
							c += 4;
						}
					}

				} else if ((msgList.get(6).equals("1e") || msgList.get(6)
						.equals("1E")) && flag != 1) {
					try {
						anaMsg.append("信息体地址：" + msgList.get(12)
								+ msgList.get(13) + msgList.get(14) + "\n");

					} catch (IndexOutOfBoundsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						anaMsg.replace(0, anaMsg.length(), "数据报文不完整！\n");
						return;
					}
					try {
						anaMsg.append("遥信状态：" + msgList.get(15) + "\n");
					} catch (IndexOutOfBoundsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						anaMsg.append("遥信状态：" + 00 + "\n");
					}
					int ms;
					int min;
					int hour;
					int day;
					int month;
					int year;
					String t1;
					String t2;
					String t3;
					String t4;
					String t5;
					String t6;
					String t7;
					try {
						t1 = msgList.get(16);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t1 = "00";
					}
					try {
						t2 = msgList.get(17);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t2 = "00";
					}
					try {
						t3 = msgList.get(18);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t3 = "00";
					}
					try {
						t4 = msgList.get(19);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t4 = "00";
					}
					try {
						t5 = msgList.get(20);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t5 = "00";
					}
					try {
						t6 = msgList.get(21);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t6 = "00";
					}
					try {
						t7 = msgList.get(22);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t7 = "00";
					}
					try {
						ms = Integer.parseInt(t1 + t2, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					try {
						min = Integer.parseInt(t3, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					try {
						hour = Integer.parseInt(t4, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					try {
						day = Integer.parseInt(t5, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					try {
						month = Integer.parseInt(t6, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					try {
						year = Integer.parseInt(t7, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					anaMsg.append("毫秒：" + t1 + t2 + "(" + ms + "毫秒)" + "\n");
					anaMsg.append("分：" + t3 + "(" + min + "分)" + "\n");
					anaMsg.append("时：" + t4 + "(" + hour + "时)" + "\n");
					anaMsg.append("日：" + t5 + "(" + day + "日)" + "\n");
					anaMsg.append("月：" + t6 + "(" + month + "月)" + "\n");
					anaMsg.append("年：" + t7 + "(" + year + "年)" + "\n");
				} else if (msgList.get(6).equals("67") && flag != 1) {
					try {
						anaMsg.append("信息体地址：" + msgList.get(12)
								+ msgList.get(13) + msgList.get(14) + "\n");

					} catch (IndexOutOfBoundsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						anaMsg.replace(0, anaMsg.length(), "数据报文不完整！\n");
						return;
					}
					int ms;
					int min;
					int hour;
					int day;
					int month;
					int year;
					String t1;
					String t2;
					String t3;
					String t4;
					String t5;
					String t6;
					String t7;
					try {
						t1 = msgList.get(15);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t1 = "00";
					}
					try {
						t2 = msgList.get(16);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t2 = "00";
					}
					try {
						t3 = msgList.get(17);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t3 = "00";
					}
					try {
						t4 = msgList.get(18);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t4 = "00";
					}
					try {
						t5 = msgList.get(19);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t5 = "00";
					}
					try {
						t6 = msgList.get(20);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t6 = "00";
					}
					try {
						t7 = msgList.get(21);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						t7 = "00";
					}
					try {
						ms = Integer.parseInt(t1 + t2, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					try {
						min = Integer.parseInt(t3, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					try {
						hour = Integer.parseInt(t4, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					try {
						day = Integer.parseInt(t5, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					try {
						month = Integer.parseInt(t6, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					try {
						year = Integer.parseInt(t7, 16);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context, "请确认输入的全是十六进制数", 0).show();
						return;
					}
					anaMsg.append("毫秒：" + t1 + t2 + "(" + ms + "毫秒)" + "\n");
					anaMsg.append("分：" + t3 + "(" + min + "分)" + "\n");
					anaMsg.append("时：" + t4 + "(" + hour + "时)" + "\n");
					anaMsg.append("日：" + t5 + "(" + day + "日)" + "\n");
					anaMsg.append("月：" + t6 + "(" + month + "月)" + "\n");
					anaMsg.append("年：" + t7 + "(" + year + "年)" + "\n");
				} else if (msgList.get(6).equals("0d")
						|| msgList.get(6).equals("0D") && flag != 1) {
					int a;
					try {
						a = Integer.parseInt(msgList.get(7), 16);
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						Toast.makeText(context, "请确认输入的十六进制数据", 0).show();
						return;
					}

					if (a >= 128) {
						String b1;
						try {
							b1 = msgList.get(12);
						} catch (IndexOutOfBoundsException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							b1 = "00";
						}
						String b2;
						try {
							b2 = msgList.get(13);
						} catch (IndexOutOfBoundsException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							b2 = "00";
						}
						String b3;
						try {
							b3 = msgList.get(14);
						} catch (IndexOutOfBoundsException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							b3 = "00";
						}
						anaMsg.append("信息体地址：" + b1 + b2 + b3 + "\n");
						for (int j = 1; j <= a - 128; j++) {
							try {
								anaMsg.append("数据" + j + "："
										+ msgList.get(j + 13) + "\n");
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								anaMsg.append("数据" + j + "：00\n");
								e.printStackTrace();
							}
						}
					} else {
						int c = 1;
						for (int j = 1; j <= a; j++) {
							String a1;
							try {
								a1 = msgList.get(c + 11);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a1 = "00";
							}
							String a2;
							try {
								a2 = msgList.get(c + 12);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a2 = "00";
							}
							String a3;
							try {
								a3 = msgList.get(c + 13);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a3 = "00";
							}
							String a4;
							try {
								a4 = msgList.get(c + 14);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a4 = "00";
							}
							String a5;
							try {
								a5 = msgList.get(c + 15);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a5 = "00";
							}
							String a6;
							try {
								a6 = msgList.get(c + 16);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a6 = "00";
							}
							String a7;
							try {
								a7 = msgList.get(c + 17);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a7 = "00";
							}
							String a8;
							try {
								a8 = msgList.get(c + 18);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								a8 = "00";
							}
							anaMsg.append("第" + j + "个信息体地址：" + a1 + a2 + a3
									+ "\n");
							anaMsg.append("第" + j + "个信息体元素：" + a4 + a5 + a6
									+ a7 + a8 + "\n");
							c += 8;
						}
					}

				}
			}
		} else {
			anaMsg.replace(0, anaMsg.length(), "数据报文不正确");
		}
		result.setText(anaMsg);
	}

	private static int hexToBinary(String string) {
		// TODO Auto-generated method stub
		int i = Integer.parseInt(string, 16);
		
		return Integer.parseInt(Integer.toBinaryString(i));
	}

	private static String TpReason(String msg) {
		// TODO Auto-generated method stub
		String reason = "";
		if (msg.equals("03")) {
			reason = "突发";
		} else if (msg.equals("05")) {
			reason = "请求、被请求";
		} else if (msg.equals("06")) {
			reason = "激活";
		} else if (msg.equals("07")) {
			reason = "激活确认";
		} else if (msg.equals("09")) {
			reason = "停止激活确认";
		} else if (msg.equals("0a") || msg.equals("0A")) {
			reason = "激活终止";
		} else if (msg.equals("14")) {
			reason = "相应站总召唤";
		} else if (Integer.parseInt(msg, 16) <= 36
				&& Integer.parseInt(msg, 16) >= 21) {
			reason = "响应第1组召唤－响应第16组召唤";
		} else if (Integer.parseInt(msg, 16) <= 41
				&& Integer.parseInt(msg, 16) >= 38) {
			reason = "响应第1组计数量－响应第4组计数量";
		} else {
			reason = "";
		}
		return reason;
	}

	private static String msgToType(String msg) {
		// TODO Auto-generated method stub
		String type = "";
		if (msg.equals("01")) {
			type = "单点遥信";
		} else if (msg.equals("09")) {
			type = "归一化遥测";
		} else if (msg.equals("0d") || msg.equals("0D")) {
			type = "浮点型遥测";
		} else if (msg.equals("1E") || msg.equals("1e")) {
			type = "SOE(事件记录)";
		} else if (msg.equals("67")) {
			type = "对时";
		} else if (msg.equals("25")) {
			type = "电度";
		} else if (msg.equals("64")) {
			type = "总召";
		} else if (msg.equals("2d") || msg.equals("2D")) {
			type = "单点遥控";
		} else if (msg.equals("2e") || msg.equals("2E")) {
			type = "双点遥控";
		} else {
			type = "不识别的类型标识";
		}
		return type;
	}
}
