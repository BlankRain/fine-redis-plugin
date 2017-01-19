### 使用説明及注意事項:
	1. 主机填写IP或者域名都可以.默认值 127.0.0.1;
	2. 端口填写数字. 默认值6379;
	3. 有认证,就填写密码.没有认证,请空着.
	4. Key填写Redis里面存储的Key.
###结果集说明:
	1. 对于二进制及起序列化后数据未进行测试及支持.
	2. 目前只支持五种数据结构的操作.[string,set,hash,list,sorted set]
	3. 返回结果集为三列. [Key ,KeyExpand ,Value]
	 - a. Key 即你的查询Key. 
	 
       如果输入的是含有匹配模式的Key,返回key,是$该模式=>$该模式返回的Key;
	 
        举个列子:  比如你 redis里有两个key,分别是 a ,b . 输入 * .则 Key为 *=>a ,*=>b
	 
      对于非匹配模式的Key,key为输入本身.
	 
      什么是匹配模式的Key? Key里面包含 [ ] * ? 的就是. 不要问为啥这样,也不要问对不对,我是这么约定的.
	 - b. KeyExpand 是展开后的Key,用来方便理解数据
	 
      对于string ,无需展开,展开后就是本身.
	 
      对于list, 需要展开,展开后是 key[index]
	 
      对于hash, 需要展开,展开后是 key + startTag + hashKey + endTag; startTag是#,endTag是: 
		
          比如,redis里存了个hash数据,key为 sayhi 值以JSON表示为{ "zh":"嗨","en","hi"}则展开后 KeyExpand为 sayhi#zh: sayhi#en:
	 
      对于set,这里是无序的,key + startTag + index + endTag; startTag是#{,  endTag是}
		
          比如,redis里存了个set数据,key为 names 值是#{ "alice","jack","smith"} KeyExpand为 names#{0} ,names#{1},names#{2}
	 
      对于sorted set, 这里有个score，KeyExpand= key + startTag + score +"-"+i +endTag,与set一样,只是多加了score进去.
	 
	 
   上面不需要太懂,实际各种数据结构的数据查几把就清楚了.
