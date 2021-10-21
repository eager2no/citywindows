
package com.chen.ga;

import java.util.List;

/**
 * @description: 染色体
 * @params:
 * @return:
 * @author: chenzhiwen
 * @dateTime: 2021/10/21 下午7:21
 */
public interface Chromosome<C extends Chromosome<C>> {
	
	List<C> crossover( C anotherChromosome );
	
	C mutate();

}
