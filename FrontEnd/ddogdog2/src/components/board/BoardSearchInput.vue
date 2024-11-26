<template>
    <div class="text-center">
        <div class="row g-2 align-items-center">
            <!-- 검색 조건 드롭다운 -->
            <div class="col-12 col-sm-3">
                <select class="form-select form-select-sm" v-model="searchInfo.key">
                    <option value="none">전체</option>
                    <option value="writer">글쓴이</option>
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                </select>
            </div>
            <!-- 검색어 입력 -->
            <div class="col-12 col-sm-5">
                <input type="text" class="form-control form-control-sm" v-model="searchInfo.word" placeholder="검색어를 입력하세요" />
            </div>
            <!-- 검색 버튼 -->
            <div class="col-6 col-sm-1">
                <button class="btn btn-primary btn-sm w-100" @click="searchBoardList" style="background-color: #4ba64b; border-color: #4ba64b">검색</button>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { ref } from "vue";
import { useBoardStore } from "@/stores/board";

const store = useBoardStore();
const searchInfo = ref({
    key: "none",
    word: "",
    orderBy: "none",
    orderByDir: "asc",
});
const searchBoardList = function () {
    store.searchBoardList(searchInfo.value);
};

const sortChange = function () {
    if (searchInfo.value.orderByDir === "asc")
        searchInfo.value.orderByDir = "desc";
    else searchInfo.value.orderByDir = "asc";
};
</script>
  
<style scoped>
/* 모바일 화면 크기 조정 */
.form-select,
.form-control {
    font-size: 0.9rem; /* 글씨 크기 조정 */
}

.row {
    margin: 0; /* 모바일 화면에서 불필요한 여백 제거 */
}

.btn-sm {
    padding: 0.3rem 0.5rem; /* 버튼 크기 조정 */
}

/* 아이콘 크기 조정 */
.bi {
    font-size: 1rem;
}
</style>
