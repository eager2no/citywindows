
package com.chen.ga;

import java.util.List;

public interface Fitness<C extends Chromosome<C>, T extends Comparable<T>> {

	/**
	 * @description: 用于选择较优解
	 * @params: [chromosome, rules]
	 * @return: T
	 * @author: chenzhiwen
	 * @dateTime: 2021/10/21 下午7:20
	 */
	T calculate(C chromosome, List<Integer> rules);

}
