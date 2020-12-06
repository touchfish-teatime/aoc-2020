package com.licky.leetcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {


    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     *  
     * <p>
     * 示例 1:
     * <p>
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * <p>
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * <p>
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     * 示例 4:
     * <p>
     * 输入: s = ""
     * 输出: 0
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static int lengthOfLongestSubstring(String s) {
        var chars = s.toCharArray();
        var linkedList = new LinkedList<Character>();
        var max = 0;
        for (char nowChar : chars) {
            if (linkedList.contains(nowChar)) {
                max = Math.max(max, linkedList.size());
                while (
                        Optional.ofNullable(linkedList.poll())
                                .map(pop -> pop != nowChar)
                                .orElse(false)
                ) {
                }
            }
            linkedList.offer(nowChar);
        }

        return Math.max(max, linkedList.size());
    }

    /**
     * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
     * <p>
     * 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
     * <p>
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums1 = [1,3], nums2 = [2]
     * 输出：2.00000
     * 解释：合并数组 = [1,2,3] ，中位数 2
     * 示例 2：
     * <p>
     * 输入：nums1 = [1,2], nums2 = [3,4]
     * 输出：2.50000
     * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
     * 示例 3：
     * <p>
     * 输入：nums1 = [0,0], nums2 = [0,0]
     * 输出：0.00000
     * 示例 4：
     * <p>
     * 输入：nums1 = [], nums2 = [1]
     * 输出：1.00000
     * 示例 5：
     * <p>
     * 输入：nums1 = [2], nums2 = []
     * 输出：2.00000
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        var length = nums1.length + nums2.length;
        if (length == 0) {
            return 0;
        }
        var array = Stream.of(nums1, nums2).flatMapToInt(Arrays::stream).sorted().toArray();
        if (length == 1) {
            return array[0];
        }
        var middle = Math.round((float) length / 2);

        if (length % 2 == 1) {
            return array[middle - 1];
        }

        return (double) (array[middle] + array[middle - 1]) / 2;

    }

    // 有待提升 用二分法
    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        var length = nums1.length + nums2.length;
        if (length == 0) {
            return 0;
        }

        int[] nums = new int[length];
        var num1Index = 0;
        var num2Index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (
                    num2Index == nums2.length ||
                            (num1Index < nums1.length && nums1[num1Index] <= nums2[num2Index])

            ) {
                nums[i] = nums1[num1Index];
                num1Index++;
            } else if (
                    num1Index == nums1.length ||
                            (num2Index < nums2.length && nums2[num2Index] <= nums1[num1Index])

            ) {
                nums[i] = nums2[num2Index];
                num2Index++;
            }
        }
        if (length == 1) {
            return nums[0];
        }
        var middle = Math.round((float) length / 2);

        if (length % 2 == 1) {
            return nums[middle - 1];
        }

        return (double) (nums[middle] + nums[middle - 1]) / 2;

    }

    /**
     * 给定一个正整数 n ，输出外观数列的第 n 项。
     * <p>
     * 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
     * <p>
     * 你可以将其视作是由递归公式定义的数字字符串序列：
     * <p>
     * countAndSay(1) = "1"
     * countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
     * 前五项如下：
     * <p>
     * 1.     1
     * 2.     11
     * 3.     21
     * 4.     1211
     * 5.     111221
     * 第一项是数字 1
     * 描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
     * 描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
     * 描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
     * 描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
     * 要 描述 一个数字字符串，首先要将字符串分割为 最小 数量的组，每个组都由连续的最多 相同字符 组成。然后对于每个组，先描述字符的数量，然后描述字符，形成一个描述组。要将描述转换为数字字符串，先将每组中的字符数量用数字替换，再将所有描述组连接起来。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/count-and-say
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }
        var string = countAndSay(n - 1);

        var chars = string.toCharArray();
        var start = 0;
        var end = 0;
        var count = 0;

        StringBuilder builder = new StringBuilder();

        for (; end < chars.length; end++) {
            if (chars[start] == chars[end]) {
                count++;
            } else {
                builder.append(count).append(chars[start]);
                start = end;
                count = 1;
            }
            if (end == chars.length - 1) {
                builder.append(count).append(chars[start]);
            }
        }

        return builder.toString();
    }

    public static int reverse(int x) {
        var num = Integer.valueOf(x).toString();
        if (num.startsWith("-")) {
            var s = num.substring(1).chars().mapToObj(Character::toString).collect(Collectors.toList());
            Collections.reverse(s);
            var result = "-" + String.join("", s);
            var re = Double.parseDouble(result);
            if (re < Integer.MIN_VALUE) {
                return 0;
            }
            return (int) re;
        } else {
            var s = num.chars().mapToObj(Character::toString).collect(Collectors.toList());
            Collections.reverse(s);
            var result = String.join("", s);
            var re = Double.parseDouble(result);
            if (re > Integer.MAX_VALUE) {
                return 0;
            }
            return (int) re;
        }
    }

    public static int reverse2(int x) {

        var y = (double) x;
        var ints = new ArrayList<Integer>();
        var fushu = y < 0;
        if (fushu) {
            y = -y;
        }

        do {
            var num = y % 10;
            ints.add((int) num);
            y = (y - num) / 10;
        } while (y != 0);

        var num = Double.parseDouble(ints.stream().map(String::valueOf).collect(Collectors.joining("")));

        if (num > Integer.MAX_VALUE) {
            return 0;
        } else if (fushu) {
            return (int) -num;
        }
        return (int) num;
    }

    public static int hammingWeight(int n) {
        int count = (n & Integer.MIN_VALUE) == Integer.MIN_VALUE ? 1 : 0;
        for (int i = 0; i < 32; i++) {
            n = n << 1;
            if ((n & Integer.MIN_VALUE) == Integer.MIN_VALUE) {
                count++;
            }
        }
        return count;
    }

    public static List<List<Integer>> generate(int numRows) {

        var generate = new ArrayList<List<Integer>>();

        for (int i = 0; i < numRows; ++i) {
            if (i == 0) {
                generate.add(new ArrayList<>() {
                    {
                        add(1);
                    }
                });
            } else {
                var newRow = new ArrayList<Integer>();
                var pre = generate.get(i - 1);
                for (int j = 0; j < i + 1; ++j) {
                    var left = j - 1;

                    if (left == -1) {
                        newRow.add(pre.get(j));
                    } else if (j == pre.size()) {
                        newRow.add(pre.get(left));
                    } else {
                        newRow.add(pre.get(j) + pre.get(left));
                    }
                }

                generate.add(newRow);
            }
        }

        return generate;
    }

    public static String longestPalindrome(String s) {
        if (s.length() == 0 || s.length() == 1) {
            return s;
        }

        var chars = s.toCharArray();
        var maxStart = 0;
        var maxEnd = 0;
        var max = 0;

        for (var start = 0; start < chars.length; ++start) {
            if(chars.length -start <max){
                break;
            }
            for (var end = chars.length-1; end >= start; --end) {
                if (isPalindrome(s.substring(start, end + 1))) {
                    if (end - start >= max) {
                        max = end - start;
                        maxStart = start;
                        maxEnd = end;
                    }
                }
            }
        }

        return s.substring(maxStart, maxEnd + 1);
    }

    public static boolean isPalindrome(String chars) {
        if (chars.length() == 1) {
            return true;
        }
        var start = 0;
        var end = chars.length() - 1;
        while (start < end) {
            if (chars.charAt(start) != chars.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public static void main(String[] args) {

        var start = System.currentTimeMillis();
        System.out.println(longestPalindrome("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"));

        var end = System.currentTimeMillis();

        System.out.println(end - start);
//        generate(5);
//        hammingWeight(0b11111111111111111111111111111101);
//        System.out.println(reverse2(-2147483648));
    }
}
