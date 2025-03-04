package com.star.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Classname: CustomerPartitioner
 * @Date: 2025/3/4 20:02
 * @Author: 聂建强
 * @Description: 自定义Partitioner分配策略
 */
public class CustomerPartitioner implements Partitioner {

    private final AtomicInteger nextPartition = new AtomicInteger(0);

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();

        if (key == null) {
            // 使用轮询方式选择分区
            int next = nextPartition.getAndIncrement();
            if (next >= numPartitions) {
                nextPartition.compareAndSet(next, 0);
            }
            System.out.println("分区值：" + next);
            return next;
        } else {
            // 如果key不为null，则使用默认的分区策略
            return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
