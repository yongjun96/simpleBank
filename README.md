# Junit Bank App

### Jpa LocalDataTime 자동으로 생성
 - domain -> base -> BaseEntity, BaseTimeEntity
    - BaseEntity : BaseTimeEntity를 상속하여 사용하고, 등록자와 사용자의 정보를 가짐
    - BaseTimeEntity : 생성날짜와 수정날짜를 가지고 있고 BaseEntity이 필요없는 경우를 대비해서 따로 생성
    - BaseClass 적용 : @EntityListeners(AuditingEntityListener.class), @MappedSuperclass
    - BankApplication 적용 : @EnableJpaAuditing


```java
//ex). BaseTimeEntity

   @CreatedDate // insert 자동 날짜 저장
   @Column(nullable = false)
   private LocalDateTime createAt;

   @LastModifiedDate // insert, update 자동 날짜 저장
   @Column(nullable = false)
   private LocalDateTime updateAt;
```

