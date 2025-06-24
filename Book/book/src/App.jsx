import { Routes, Route } from "react-router-dom";
import LandingPage from "./Pages/LandingPage";
import AuthPage from "./Pages/AuthPage";
import Books from "./Pages/Books"; 
import UserBooks from "./Pages/UserBooks";
import BookDetails from "./Pages/BookDetails";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";





function App() {
  return (
    <>
      
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/auth" element={<AuthPage />} />
       <Route path="/books" element={<Books />} /> 
        <Route path="/user/books" element={<UserBooks />} />
        <Route path="/books/:id" element={<BookDetails />} />
    </Routes>
        <ToastContainer />
        </>
  );
}

export default App;
