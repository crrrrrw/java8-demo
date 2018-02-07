# 为什么要用Java8
- **elasticsearch:** Elasticsearch requires Java 8 or later. Use the official Oracle distribution or an open-source distribution such as OpenJDK.
- **dubbo:** Requires JDK1.8+, if you use lower version, see 1.6+, use 2.5.5
- **spring:** JDK 8+ for Spring Framework 5.x
......

很多主流框架已经使用java8进行升级开发，java8是趋势，是时候一起拥抱java8了.

# java8语言新特性
- 函数式编程
- Lambda表达式
- 接口的默认方法和静态方法
- stream API
- 新的类库：Optional,Streams,Date/Time API (JSR 310)...
- JVM新特性

### 1.Lambda表达式
Lambda允许把函数作为一个方法的参数（函数作为参数传递进方法中），或者把代码看成数据。

##### 1.1 lambda的基本语法是：
```
(parameters) -> expression
```
或者
```
(parameters) -> { statements; }
```


eg:java8中有效的lambda表达式：
1. 一个String类型的参数并返回一个int。Lambda中没有return语句，因为已经隐含了return。
```
(String s) -> s.length()
```

2. 两个int类型的参数而没有返回值。
```
(int x, int y) -> {
    System.out.println("Result:");
    System.out.println(x+y);
}
```
3. 没有参数，返回一个int
```
() -> 1
```
##### 1.2 函数式接口
- 函数式接口(函数式接口就是只定义一个抽象方法的接口)
- 函数描述符(函数式接口的抽象方法的签名 就是 lambda表达式的签名)

java8中接口的变化:
Java 8用**默认方法**与**静态方法**这两个新概念来扩展接口的声明。
此功能是为了向后兼容性增加，使旧接口可用于利用JAVA8，lambda表达式的能力
默认方法与抽象方法不同之处在于抽象方法必须要求实现，但是默认方法则没有这个要求。


在实际使用过程中，函数式接口是容易出错的：如有某个人在接口定义中增加了另一个方法，这时，这个接口就不再是函数式的了，并且编译过程也会失败。为了克服函数式接口的这种脆弱性并且能够明确声明接口作为函数式接口的意图，Java 8增加了一种特殊的注解@FunctionalInterface（Java 8中所有类库的已有接口都添加了@FunctionalInterface注解）。


目前已存在的函数式接口有：Comparable、Runnable和Callable等。
java8在java.util.function中有引入了很多个新的函数式接口:
- Predicate<T>  输入参数为类型T， 输出为类型boolean， 记作 T -> boolean
- Consumer<T>  输入参数为类型T， 输出为void， 记作 T -> void
- Function<T,R>   输入参数为类型T， 输出为类型R， 记作 T -> R
- Supplier<T>   没有输入参数， 输出为类型T， 记作 void -> T
...


##### 1.3方法引用
方法引用让你可以重复使用现有的方法定义，并像Lambda一样传递它们。
当你需要使用方法引用时，目标引用放在分隔符::前，方法的名称放在后面。

**三种方法引用：**
1. 指向静态方法的方法引用。
lambda表达式:   (args) -> ClassName.staticMethod(args)
方法引用:       ClassName::staticMethod

比如
```
s -> Integer.valueOf(s) 等价于 Integer::valueOf
```

2. 指向任意类型实例方法的方法引用。
lambda表达式:   (arg0, rest) -> arg0.instanceMethod(rest)
方法引用:       ClassName::instanceMethod
(上面args0是ClassName类型的)

比如
```
(str, integer) -> str.substring(integer) 等价于 String::substring
```


3. 指向现有对象的实例方法的方法引用。
lambda表达式:   (args) -> expr.instanceMethod(args)
方法引用:       expr::instanceMethod

比如
```
(Apple arg) -> appleExpr.compareAppleWight(arg) 等价于 appleExpr::compareAppleWight
```

- 构造函数的方法引用。
对于一个现有构造函数，你可以利用它的名称和关键字new来创建它的一个引用：
ClassName::new。它的功能与指向静态方法的引用类似。

比如创建一个Apple对象。
空参构造器Apple()：
```
Supplier<Apple> c1 = Apple::new;
Apple a1 = c1.get();

等价于

Supplier<Apple> c1 = () -> new Apple();
Apple a1 = c1.get();
```
两个参数的构造函数Apple(String color, Integer weight)：
```
BiFunction<Integer, String, Apple> biFunction = Apple::new;
Apple apple2 = biFunction.apply(155, "green");

等价于

BiFunction<Integer, String, Apple> biFunction = (weight, color) -> new Apple(weight, color);
Apple apple2 = biFunction.apply(155, "green");
```

##### 1.4 复合lambda表达式
1. 比较器(Comparator<T>)复合(reversed(),thenComparing())
2. 谓词(Predicate<T>)复合(negate(),and(),or())
3. 函数(Function<T, V>)复合(compose(),andThen(),identity())

### 2.Stream API
Java 8 中的 Stream 是对集合（Collection）对象功能的增强，它专注于对集合对象进行各种非常便利、高效的聚合操作（aggregate operation），或者大批量数据操作 (bulk data operation)。

Stream API(java.util.stream.*)可以让你的代码具备：
- 声明性：更简洁，更易读
- 可复合：更灵活
- 可并行：性能更好



##### 2.1 什么是流
流不是一种数据结构，而是处理集合元素的相关计算，更像一个高级的 Iterator。单向，不可往复，数据只能遍历一次。

**如何使用流？**
1. 一个数据源（如集合）来执行一个查询；
2. 一个中间操作链，形成一条流的流水线；
3. 一个终端操作，执行流水线，并能生成结果。

##### 2.2 使用流
- 筛选、切片和匹配
- 查找、匹配和归约
- 使用数值范围等数值流
- 从多个源创建流
- 无限流

###### 2.2.1 筛选，切片
- filter  (筛选过滤)
- distinct (去重)
- limit (截取)
- skip  (跳过)

###### 2.2.2 映射
- map (映射:接受一个函数作为参数。这个函数会被应用到每个元素上，并将其映射成一个新的元素)
- flatMap (flatmap方法让你把一个流中的每个值都换成另一个流，然后把所有的流连接起来成为一个流。)

###### 2.2.3 查找和匹配
- anyMatch (检查谓词是否至少匹配一个元素)
- allMatch (检查谓词是否匹配所有元素)
- noneMatch (检查是否没有任何元素与给定的谓词匹配)
- findAny (返回当前流中的任意元素Optional<T>。它可以与其他流操作结合使用。)
- findFirst (返回流中第一个元素Optional<T>)

###### 2.2.4 reduce
这个方法的主要作用是把 Stream 元素组合起来。它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面 Stream 的第一个、第二个、第 n 个元素组合。从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce。

- Optional<T> reduce(BinaryOperator<T> accumulator);
- T reduce(T identity, BinaryOperator<T> accumulator);
- U reduce(U identity,BiFunction<U, ? super T, U>      accumulator,BinaryOperator< U > combiner);

###### 2.2.5 数值流
- IntStream
- DoubleStream
- LongStream

###### 2.2.6 创建流
- 由值创建流(Stream.of)
- 由数组创建流(Arrays.stream(array))
- 由文件生成流(java.nio.file.Files)
- 由函数生成流：创建无限流(Stream.iterate和Stream.generate)


##### 2.3 收集器
- 用Collectors类创建和使用收集器
- 将数据流归约为一个值
- 汇总：归约的特殊情况
- 数据分组和分区
- 自定义收集器

###### 2.3.1 归约和汇总

Stream.reduce 与 Stream.collect的区别：
Stream.reduce，常用的方法有average, sum, min, max, and count，返回单个的结果值，并且reduce操作每处理一个元素总是创建一个新值。
Stream.collect修改现存的值，而不是每处理一个元素，创建一个新值。

###### 2.3.2 数据分组
- 一级分组
```
Map<Dish.Type, List<Dish>> groupByType = menuList.stream().collect(groupingBy(Dish::getType));
```
- 多级分组
```
Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupByTypeAndCalories = menuList.stream().collect(
            groupingBy(Dish::getType,
                    groupingBy(dish -> {
                        if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                        else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                        else return CaloricLevel.FAT;
                    }))
    );
```
- 按子组收集数据
```
Map<Dish.Type, Long> groupByTypeToCount = menuList.stream().collect(groupingBy(Dish::getType, counting()));
```
- 分区(分区是一种特殊的分组，结果map至少包含两个不同的分组——一个true，一个false。)
```
Map<Boolean, List<Dish>> partitionByVegeterian =
                menuList.stream().collect(partitioningBy(Dish::isVegetarian));
```
###### 2.3.3 Collector接口

```
public interface Collector<T, A, R> {
    Supplier<A> supplier()
    BiConsumer<A, T> accumulator()
    Function<A, R> finisher()
    BinaryOperator<A> combiner()
    Set<Characteristics> characteristics()
}
```
Collector接口的三个泛型：
- T：stream在调用collect方法收集前的数据类型
- A：A是T的累加器，遍历T的时候，会把T按照一定的方式添加到A中，换句话说就是把一些T通过一种方式变成A
- R：R可以看成是A的累加器，是最终的结果，是把A汇聚之后的数据类型，换句话说就是把一些A通过一种方式变成R

通过自定义ToList收集器理解接口方法：
- Supplier<A> supplier()
怎么创建一个累加器（这里对应的是如何创建一个List）

- BiConsumer<A, T> accumulator()
怎么把一个对象添加到累加器中（这里对应的是如何在List里添加一个对象，当然是调用add方法）

- Function<A, R> finisher()
其实就是怎么把A转化为R，由于是toList，所以A和R是一样的类型，这里其实用就是Function.identity

- BinaryOperator<A> combiner()
它定义了对流的各个子部分进行并行处理时，各个子部分归约所得的累加器要如何合并（这里对应的是如何把List和List合并起来，当然是调用addAll，这里由于最终要返回List，所以A和R是一个类型，都是List所以才调用addAll）

- Set<Characteristics> characteristics()
会返回一个不可变的Characteristics集合，它定义
了收集器的行为——尤其是关于流是否可以并行归约，以及可以使用哪些优化的提示，toList这里只用了Characteristics.IDENTITY_FINISH

##### 2.4 并行数据处理

**并行流：**
可以通过对收集源调用parallelStream方法来把集合转换为并行流。并行流就是一个把内容分成多个数据块，并用不同的线程分别处理每个数据块的流。

**问题:并行流用的线程是从哪儿来的？有多少个？怎么自定义这个过程呢？**
并行流内部使用了默认的ForkJoinPool，它默认的线程数量就是你的处理器数量，这个值是由Runtime.getRuntime().available-Processors()得到的。
但是你可以通过系统属性java.util.concurrent.ForkJoinPool.common.parallelism 来改变线程池大小，如下所示：
System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","12");
这是一个全局设置，因此它将影响代码中所有的并行流。反过来说，目前还无法专为某个并行流指定这个值。一般而言，让ForkJoinPool的大小等于处理器数量是个不错的默认值，除非你有很好的理由，否则我们强烈建议你不要修改它。

**使用并行流：**
本地测试的过程中，并行流比顺序流效果差。原因可能与机器，处理的数据量，使用并行流的方式等有关系。

是否使用并行流需考虑如下几种情况：
1. 留意装箱。(使用（IntStream、LongStream、DoubleStream来避免装箱拆箱)
2. 有些操作本身在并行流上的性能就比顺序流差。(limit,findFirst等依赖于元素顺序的操作)
3. 考虑流的操作流水线的总计算成本。设N是要处理的元素的总数，Q是一个元素通过流水线的大致处理成本，则N*Q就是这个对成本的一个粗略的定性估计。Q值较高就意味着使用并行流时性能好的可能性比较大。
4. 数据量较小的情况不适合并行流。
5. 考虑流背后的数据结构是否易于分解。
6. 流自身的特点，以及流水线中的中间操作修改流的方式，都可能会改变分解过程的性能。
7. 考虑终端操作中合并步骤的代价是大是小（例如Collector中的combiner方法）。

流的数据源
源 | 可分解性
---|---
ArrayList | 极佳
LinkedList | 差
IntStream.range | 极佳
Stream.iterate | 差
HashSet | 好
TreeSet | 好


### 3.Optional使用
- 使用Optional避免null引用
- 整洁代码中对null的检查
- Optional的使用

java中的null带来了种种问题：典型常见，使代码膨胀，自身无意义等等等。


方法| 描述
---|---
empty       | 返回一个空的Optional 实例
filter      | 如果值存在并且满足提供的谓词，就返回包含该值的Optional 对象；否则返回一个空的Optional 对象
flatMap     | 如果值存在，就对该值执行提供的mapping函数调用，返回一个Optional 类型的值，否则就返回一个空的Optional 对象
get         | 如果该值存在，将该值用Optional封装返回，否则抛出一个NoSuchElementException 异常
ifPresent   | 如果值存在，就执行使用该值的方法调用，否则什么也不做
isPresent   | 如果值存在就返回true，否则返回false
map         | 如果值存在，就对该值执行提供的mapping 函数调用
of          | 将指定值用Optional封装之后返回，如果该值为null，则抛出一个NullPointerException异常
ofNullable  | 将指定值用Optional封装之后返回，如果该值为null，则返回一个空的Optional 对象
orElse      | 如果有值则将其返回，否则返回一个默认值
orElseGet   | 如果有值则将其返回，否则返回一个由指定的Supplier接口生成的值
orElseThrow | 如果有值则将其返回，否则抛出一个由指定的Supplier接口生成的异常


注意：Optional 无法序列化


### 4.新的日期和时间API
新的 java.time 中包含了所有关于：
时钟（Clock）、本地日期（LocalDate）、本地时间（LocalTime）、本地日期时间（LocalDateTime）、时区（ZonedDateTime）和持续时间（Duration）的类。

历史悠久的 Date 类新增了 toInstant() 方法，用于把 Date 转换成新的表示形式。这些新增的本地化时间日期 API 大大简化了了日期时间和本地化的管理。
目前Java8新增了java.time包定义的类表示日期-时间概念的规则，很方便使用；最重要的一点是**值不可变，且线程安全**。

本地日期时间API:
- LocalDate(年月日)
- LocalTime(时分秒)
- localDateTime(年月日时分秒)

时区API：
- ZonedDateTime

时钟API：
- Clock

计算日期时间差API：
- Period(处理有关基于时间的日期数量。)
- Duration(处理有关基于时间的时间量。)

时间格式化API
- DateTimeFormatter(DateTimeFormatter实例都是线程安全
的)

### 5. 其他
1. java类库标准base64编码使用方式:
```
final String text = "测试Abc123!!￥￥";
final String encoded = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
System.out.println(encoded); // 5rWL6K+VQWJjMTIzISHvv6Xvv6U=
final String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
System.out.println(decoded); // 测试Abc123!!￥￥


// url encode
final String url = "http://www.jinhui365.com/abc?foo=中文&￥%&bar=hello123&baz=http://abc/def123";
final String encoded2 = Base64.getUrlEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8));
System.out.println(encoded2); // aHR0cDovL3d3dy5qaW5odWkzNjUuY29tL2FiYz9mb2895Lit5paHJu-_pSUmYmFyPWhlbGxvMTIzJmJhej1odHRwOi8vYWJjL2RlZjEyMw==
final String decoded2 = new String(Base64.getUrlDecoder().decode(encoded2), StandardCharsets.UTF_8);
System.out.println(decoded2); // http://www.jinhui365.com/abc?foo=中文&￥%&bar=hello123&baz=http://abc/def123

```

2. jvm的变化:
PermGen空间被移除了，取而代之的是Metaspace（JEP 122）。  JVM选项    -XX:PermSize与-XX:MaxPermSize分别被
-XX:MetaSpaceSize与-XX:MaxMetaspaceSize所代替。



# 最后
Java8 作为 Java 语言的一次重大发布，包含语法上的更改、新的方法与数据类型，以及一些能默默提升应用性能的隐性改善。而且java8有利于提高开发生产力，对于开发者来说是好事，也是趋势。但是生产中使用java8可能存在风险，在正式使用Java8之前，不妨先体验一下java8的神奇。

附《java8 in action》源码:https://github.com/java8/Java8InAction.git

