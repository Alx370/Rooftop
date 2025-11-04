import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "../components/Header/Header.js";
import Footer from "../components/Footer/Footer.js";

import Home from "../pages/Home/home.jsx";
import Catalogo from "../pages/Catalogo/catalogo.jsx";
import Login from "../pages/Login/login.jsx";
import ChiSiamo from "../pages/ChiSiamo/chisiamo.jsx";
import Valutazione from "../pages/Valutazione/valutazione.jsx";
import NotFound from "../pages/NotFound/notfound.jsx";

const AppRoutes = () => {
  return (
    <div className="container">
      <BrowserRouter>
        <Header />
        <main >
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/catalogo" element={<Catalogo />} />
            <Route path="/chi-siamo" element={<ChiSiamo />} />
            <Route path="/valutazione" element={<Valutazione />} />
            <Route path="/login" element={<Login />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </main>
        <Footer />
      </BrowserRouter>
    </div>
  );
};

export default AppRoutes;