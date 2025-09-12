# React.js 학습 내용 정리 (Summary)

Gemini와 함께 학습한 React.js 핵심 개념들을 정리한 문서입니다.

---

## 1. JSX에서 파일 확장자 생략

- **질문:** `import BoardList from './pages/BoardList'` 처럼 `.jsx` 확장자 없이도 동작하는 이유는 무엇인가요?
- **답변:** 이는 React나 JavaScript의 기본 기능이 아닌, **Vite와 같은 모듈 번들러(Module Bundler)**의 기능입니다. 모듈 번들러가 import 경로를 해석할 때 `.js`, `.jsx`, `.ts` 등 미리 설정된 확장자를 자동으로 붙여서 파일을 찾아주기 때문에 확장자를 생략할 수 있습니다.

---

## 2. 컴포넌트의 Return 규칙

- **질문:** React 컴포넌트의 `return` 안에는 태그가 하나만 있어야 하나요?
- **답변:** 네, 맞습니다. 모든 React 컴포넌트는 **하나의 최상위(Root) 요소**만 반환해야 합니다.

- **이유:** JSX는 JavaScript 함수 호출로 변환되는데, 함수는 하나의 값만 반환할 수 있기 때문입니다.

- **해결책:**
  1.  **`<div>`로 감싸기:** 가장 간단하지만, 불필요한 DOM 노드가 생성됩니다.
      ```jsx
      return (
        <div>
          <h1>제목</h1>
          <p>내용</p>
        </div>
      );
      ```
  2.  **`<Fragment>` 사용하기 (권장):** DOM에 추가 노드를 생성하지 않고 요소들을 묶을 수 있습니다.
      ```jsx
      // 축약형 문법
      return (
        <>
          <h1>제목</h1>
          <p>내용</p>
        </>
      );
      ```

---

## 3. React Router 기본 사용법 (`App.jsx` 리뷰)

- **문제점 1: 잘못된 import 경로**
  - `import {Routes} from "../node_modules/react-router-dom";` (X)
  - `node_modules`에서 직접 파일을 가져오는 것은 잘못된 방식입니다.
  - **수정:** `import { Routes } from "react-router-dom";` (O)

- **문제점 2: 컴포넌트 중복 렌더링**
  - `<Routes>` 태그의 **바깥과 안**에 동일한 컴포넌트를 렌더링하면, 화면에 컴포넌트가 중복으로 표시됩니다.
  - 라우팅을 통해 특정 경로에서 특정 화면만 보여주려면, 해당 컴포넌트들은 `<Routes>` 내부에만 위치해야 합니다.

- **올바른 구조:**
  ```jsx
  function App() {
    return (
      <>
        <h1>게시판</h1>
        <hr />
        <Routes>
          <Route path="/" element={<BoardList />} />
          <Route path="/new" element={<BoardForm />} />
        </Routes>
      </>
    );
  }
  ```
- **필수 설정:** `react-router-dom`을 사용하려면 `main.jsx`에서 `<App />`을 `<BrowserRouter>`로 감싸야 합니다.

---

## 4. React 파일 이름 규칙

- **질문:** 왜 `api.jsx` 같은 파일은 소문자로 시작하나요?
- **답변:** 파일이 **무엇을 export 하느냐**에 따라 규칙이 달라집니다.

- **대문자 시작 (PascalCase, 예: `BoardList.jsx`):**
  - **React 컴포넌트**를 export하는 파일.
  - JSX에서 `<BoardList />` 처럼 HTML 태그와 구분하기 위함.

- **소문자 시작 (camelCase, 예: `api.js`, `utils.js`):**
  - 컴포넌트가 아닌 **일반 함수, 객체, 변수, 커스텀 훅** 등을 export하는 파일.
  - `api.jsx`는 UI가 아닌 데이터 통신용 함수를 export하므로 소문자로 시작합니다.
