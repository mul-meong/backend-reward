package com.mulmeong.reward.point.infrastructure;

import com.mulmeong.reward.point.domain.entity.PointHistory;
import com.mulmeong.reward.point.domain.entity.QPointHistory;
import com.mulmeong.reward.point.domain.model.SortType;
import com.mulmeong.reward.point.dto.CursorPage;
import com.mulmeong.reward.point.dto.in.PointHistoryFilterRequestDto;
import com.mulmeong.reward.point.dto.model.BasePaginationDto;
import com.mulmeong.reward.point.dto.out.PointHistoryDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PointHistoryRepositoryCustomImpl implements PointHistoryRepositoryCustom {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    private final JPAQueryFactory jpaQueryFactory;
    private final QPointHistory pointHistory = QPointHistory.pointHistory;

    @Override
    public CursorPage<PointHistoryDto> getMemberPointHistories(PointHistoryFilterRequestDto requestDto) {

        String memberUuid = requestDto.getMemberUuid();
        BooleanBuilder builder = new BooleanBuilder();

        // memberUuid가 같은 경우에만 조회
        Optional.ofNullable(memberUuid).ifPresent(memberId ->
                builder.and(pointHistory.memberUuid.eq(memberId)));

        // 사유별 조회
        Optional.ofNullable(requestDto.getReason()).ifPresent(reason ->
                builder.and(pointHistory.reason.eq(reason)));

        // 이력 타입별 조회
        Optional.ofNullable(requestDto.getHistoryType()).ifPresent(historyType ->
                builder.and(pointHistory.historyType.eq(historyType)));

        return getPointHistoriesWithPagination(requestDto, builder);
    }

    private CursorPage<PointHistoryDto> getPointHistoriesWithPagination(
            BasePaginationDto requestDto,
            BooleanBuilder builder) {

        // lastId가 존재하는 경우 해당 ID 이후부터 조회
        Optional.ofNullable(requestDto.getLastId()).ifPresent(lastId ->
                builder.and(pointHistory.id.lt(lastId)));

        int currentPageNo = Optional.ofNullable(requestDto.getPageNo()).orElse(DEFAULT_PAGE_NUMBER);
        int currentPageSize = Optional.ofNullable(requestDto.getPageSize()).orElse(DEFAULT_PAGE_SIZE);
        int offset = Math.max(0, (currentPageNo - 1) * currentPageSize);

        List<PointHistory> content = jpaQueryFactory
                .selectFrom(pointHistory)
                .where(builder)
                .orderBy(determineSortOrder(pointHistory, requestDto.getSortType()))
                .offset(offset)
                .limit(currentPageSize + 1)
                .fetch();

        Long nextCursor = null;
        boolean hasNext = false; // 다음 페이지의 커서 처리 및 hasNext 여부 판단

        if (content.size() > currentPageSize) {
            hasNext = true;
            nextCursor = content.get(currentPageSize - 1).getId(); // 마지막 항목의 ID를 커서로 설정
            content = content.subList(0, currentPageSize); // 실제 페이지 사이즈 만큼 자르기
        }

        return new CursorPage<>(content.stream().map(PointHistoryDto::fromEntity).toList(),
                nextCursor, hasNext, requestDto.getPageSize(), requestDto.getPageNo());
    }

    private OrderSpecifier<?> determineSortOrder(QPointHistory pointHistory, SortType sortType) {
        return switch (sortType) {
            case LATEST -> pointHistory.createdAt.desc();
            case OLDEST -> pointHistory.createdAt.asc();
            default -> pointHistory.createdAt.desc();
        };
    }
}
