public interface GoodStoreRepository extends JpaRepository<GoodStore, Long> {
    boolean existsByBusinessNumber(String businessNumber);
}