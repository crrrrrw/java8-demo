package com.crw.java8.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

/**
 * java8 新版本api
 */
public class TimeApiExamples {

    public static void main(String[] args) throws InterruptedException {
        // testLocalDateTime();
        // testDuration();
        // testInstant();
        // testTemporalAdjuster();
        // testZonedDateTime();
        // testClock();
        testDateTimeFormatter();
    }

    public static void testLocalDateTime() {
        // 使用默认时区时钟瞬时时间创建 Clock.systemDefaultZone() -->即相对于
        // ZoneId.systemDefault()默认时区
        System.out.println("------------now method-----------");

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        // 自定义时区
        LocalDateTime now2 = LocalDateTime.now(ZoneId.of("Europe/Paris"));
        System.out.println(now2);// 会以相应的时区显示日期
        // 自定义时钟
        Clock clock = Clock.system(ZoneId.of("Asia/Dhaka"));
        LocalDateTime now3 = LocalDateTime.now(clock);
        System.out.println(now3);// 会以相应的时区显示日期

        System.out.println("------------LocalDateTime of -----------");

        // 不需要写什么相对时间 如java.util.Date 年是相对于1900 月是从0开始
        // 2018-01-31 23:59
        LocalDateTime d1 = LocalDateTime.of(2018, 1, 31, 23, 59);
        System.out.println(d1);
        // 年月日 时分秒 纳秒
        LocalDateTime d2 = LocalDateTime.of(2018, 1, 31, 23, 59, 59, 11);
        System.out.println(d2);
        // 使用瞬时时间 + 时区
        Instant instant = Instant.now();
        System.out.println(instant);
        LocalDateTime d3 = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        System.out.println(d3);

        System.out.println("------------LocalDateTime parse -----------");

        // 解析String--->LocalDateTime
        LocalDateTime d4 = LocalDateTime.parse("2018-01-31T23:59");
        System.out.println(d4);
        LocalDateTime d5 = LocalDateTime.parse("2018-01-31T23:59:59.999");// 999毫秒
        // 等价于999000000纳秒
        System.out.println(d5);
        // 使用DateTimeFormatter API 解析 和 格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime d6 = LocalDateTime.parse("2018/01/31 23:59:59", formatter);
        System.out.println(formatter.format(d6));


        System.out.println("------------LocalDateTime getXxx -----------");
        // 时间获取
        System.out.println(d6.getYear());
        System.out.println(d6.getMonth());
        System.out.println(d6.getDayOfYear());
        System.out.println(d6.getDayOfMonth());
        System.out.println(d6.getDayOfWeek());
        System.out.println(d6.getHour());
        System.out.println(d6.getMinute());
        System.out.println(d6.getSecond());
        System.out.println(d6.getNano());

        System.out.println("------------LocalDateTime +- -----------");
        // 时间增减
        LocalDateTime d7 = d6.minusDays(1);
        LocalDateTime d8 = d7.plus(1, IsoFields.QUARTER_YEARS);
        LocalDateTime d9 = d8.with(ChronoField.MONTH_OF_YEAR, 10);
        System.out.println(d6);
        System.out.println(d7);
        System.out.println(d8);
        System.out.println(d9);
        // LocalDate 即年月日 无时分秒
        // LocalTime 即时分秒 无年月日
        // API和LocalDateTime类似就不演示了
    }

    public static void testDuration() {
        // 表示两个瞬时时间的时间段
        Duration d1 = Duration.between(Instant.ofEpochMilli(System.currentTimeMillis() - 123456789), Instant.now());
        // 得到相应的时差
        System.out.println("相差天数:" + d1.toDays());
        System.out.println("相差小时数:" + d1.toHours());
        System.out.println("相差分钟数:" + d1.toMinutes());
        System.out.println("相差毫秒数" + d1.toMillis());
        System.out.println("相差纳秒数:" + d1.toNanos());
        // 1天时差 类似的还有如ofHours()
        Duration d2 = Duration.ofDays(1);
        System.out.println("1天时差相差天数:" + d2.toDays());

        LocalDateTime ldt1 = LocalDateTime.of(2018, 2, 5, 11, 43, 45);
        LocalDateTime ldt2 = LocalDateTime.of(2018, 4, 5, 11, 43, 45);
        Duration d3 = Duration.between(ldt1, ldt2);
        System.out.println("2018-02-05与2018-04-05相差天数:" + d3.toDays());

        Period tenDays = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
        System.out.println("10天相差天数:" + tenDays.getDays());
        System.out.println("3周相差天数:" + threeWeeks.getDays());
        System.out.println("两年六个月一天相差年数:" + twoYearsSixMonthsOneDay.getYears());
    }

    public static void testInstant() {
        // 瞬时时间 相当于以前的System.currentTimeMillis()
        Instant instant1 = Instant.now();
        System.out.println(instant1.getEpochSecond());// 精确到秒 得到相对于1970-01-01
        // 00:00:00 UTC的一个时间
        System.out.println(instant1.toEpochMilli()); // 精确到毫秒
        Clock clock1 = Clock.systemUTC(); // 获取系统UTC默认时钟
        Instant instant2 = Instant.now(clock1);// 得到时钟的瞬时时间
        System.out.println(instant2.toEpochMilli());
        Clock clock2 = Clock.fixed(instant1, ZoneId.systemDefault()); // 固定瞬时时间时钟
        Instant instant3 = Instant.now(clock2);// 得到时钟的瞬时时间
        System.out.println(instant3.toEpochMilli());// equals instant1
    }


    public static void testZonedDateTime() {
        // 即带有时区的date-time 存储纳秒、时区和时差（避免与本地date-time歧义）。
        // API和LocalDateTime类似，只是多了时差(如2018-02-02T17:43:15.433+08:00[Asia/Shanghai])
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
        ZonedDateTime now2 = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        System.out.println(now2);
        // 其他的用法也是类似的
        ZonedDateTime z1 = ZonedDateTime.parse("2018-01-31T23:59:59Z[Europe/Paris]");
        System.out.println(z1);
    }

    public static void testClock() throws InterruptedException {
        // 时钟提供给我们用于访问某个特定 时区的 瞬时时间、日期 和 时间的。
        Clock c1 = Clock.systemUTC(); // 系统默认UTC时钟（当前瞬时时间
        System.out.println("        时钟当前毫秒数:" + c1.millis()); // 每次调用将返回当前瞬时时间（UTC）
        Thread.sleep(1000);
        System.out.println("一秒后，时钟当前毫秒数:" + c1.millis());

        //相当于System.currentTimeMillis()）
        Clock c2 = Clock.systemDefaultZone(); // 系统默认时区时钟（当前瞬时时间）
        System.out.println("系统默认时区当前毫秒数:" + c2.millis());

        Clock c31 = Clock.system(ZoneId.of("Europe/Paris")); // 巴黎时区
        System.out.println("巴黎时区：" + c31.instant()); // 每次调用将返回当前瞬时时间（UTC）
        Clock c32 = Clock.system(ZoneId.of("Asia/Shanghai"));// 上海时区
        System.out.println("上海时区:" + c32.instant());// 每次调用将返回当前瞬时时间（UTC）

        Clock c4 = Clock.fixed(Instant.now(), ZoneId.of("Asia/Shanghai"));// 固定上海时区时钟
        System.out.println("        固定时钟:" + c4.millis());
        Thread.sleep(1000);
        System.out.println("一秒后，固定时钟:" + c4.millis()); // 不变 即时钟时钟在那一个点不动

        Clock c5 = Clock.offset(c1, Duration.ofSeconds(2)); // 相对于系统默认时钟两秒的时钟
        System.out.println("     当前毫秒数:" + c1.millis());
        System.out.println("2秒后当前毫秒数:" + c5.millis());
    }

    public static void testDateTimeFormatter() {
        LocalDate date = LocalDate.of(2018, 2, 5);
        String format1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        String format2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("yyyyMMdd:" + format1);
        System.out.println("yyyy-MM-dd:" + format2);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        String format3 = date.format(df);
        System.out.println("yyyy年MM月dd日:" + format3);
    }


    public static void testTemporalAdjuster() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("      当前时间:" + now);
        LocalDateTime firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("  本月的第一天:" + firstDayOfMonth);
        System.out.println("本月的最后一天:" + lastDayOfMonth);

        LocalDateTime nextWorkingDay = now.with(new NextWorkingDay());
        System.out.println("  下一个工作日:" + nextWorkingDay);
    }

    /**
     * 自定义 TemporalAdjuster，计算下一个工作日
     */
    private static class NextWorkingDay implements TemporalAdjuster {
        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
            if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }
    }
}
