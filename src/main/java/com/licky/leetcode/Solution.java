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
     *
     * @param nums1
     * @param nums2
     * @return
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

    public static String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }

        var string = countAndSay(n - 1);

        Queue<Character> chars = string.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(LinkedList::new));

        Character nowChar = Character.MIN_VALUE;
        var count = 0;
        StringBuilder result = new StringBuilder();
        do {
            var poll = chars.poll();
            if (nowChar == poll) {
                count++;
            } else {
                result.append(count).append(nowChar);
                count = 0;
                nowChar = poll;
            }
        } while (nowChar != null);
        return result.toString();

    }

    public static void main(String[] args) {
        var re = countAndSay(2);
        System.out.println(re);
    }
}
