<template>
    <div class="container mt-3">
        <h4 class="text-center mb-4">게시글 목록</h4>
        <hr>
        <!-- 반응형 테이블 -->
        <div class="table-wrapper table-responsive">
            <table class="table table-bordered table-hover text-center">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">번호</th>
                        <th scope="col">제목</th>
                        <th scope="col" class="writer-column">글쓴이</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="board in store.boardList" :key="board.id">
                        <td>{{ board.boardId }}</td>
                        <td>
                            <RouterLink :to="`/board/${board.boardId}`" class="text-decoration-none" style="color:black">
                                {{ board.title }}
                            </RouterLink>
                        </td>
                        <td>{{ board.writer }}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- 검색 입력창 -->
        <div class="mt-4">
            <BoardSearchInput />
        </div>
    </div>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { onMounted } from 'vue';
import BoardSearchInput from './BoardSearchInput.vue';

const store = useBoardStore();
onMounted(() => {
    store.getBoardList();
});
</script>

<style scoped>
.container {
    max-width: 360px; /* 화면 크기에 맞게 조정 */
}

.table-wrapper {
    height: 480px; /* 테이블 전체 높이 고정 */
    overflow-y: auto; /* 내용이 많을 경우 스크롤 생성 */
    border: 1px solid #dee2e6; /* 테두리 추가 */
    border-radius: 5px; /* 모서리 둥글게 */
}

.table {
    font-size: 0.9rem; /* 모바일에서 가독성을 위한 폰트 크기 조정 */
}

.text-center {
    font-weight: bold;
}

.text-decoration-none {
    color: #007bff;
}

.text-decoration-none:hover {
    color: #0056b3;
    text-decoration: underline;
}

/* 글쓴이 열의 넓이 조정 */
.writer-column {
    width: 30%; /* 글쓴이 열을 약간 더 넓게 설정 */
}

th:nth-child(2) {
    width: 40%; /* 제목 열의 넓이를 적절히 유지 */
}

th:nth-child(1) {
    width: 15%; /* 번호 열의 넓이를 상대적으로 줄임 */
}
</style>
