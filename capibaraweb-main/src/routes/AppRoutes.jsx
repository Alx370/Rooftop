import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "../components/Header/Header.tsx";
import Footer from "../components/Footer/Footer.tsx";

import Home from "../pages/Home/home.jsx";
import Registrati from "../pages/Registrati/registrati.jsx";
import Login from "../pages/Login/login.jsx";
import ChiSiamo from "../pages/ChiSiamo/chisiamo.jsx";
import Valutazione from "../pages/Valutazione/valutazione.jsx";
import FAQ from "../pages/FAQ/faq.jsx";
import NotFound from "../pages/NotFound/notfound.jsx";
import Agente from "../pages/Agente/agente.jsx";
import FaqList from "../pages/FaqList/FaqList.jsx";
import FormBuy from "../components/Forms/FormBuy/formbuy.jsx";
import ScrollToTop from "../components/Scroll/ScrollToTop.jsx";

const AppRoutes = () => {
  return (
    <div className="container">
      <BrowserRouter>
        <ScrollToTop />
        <Header />
        <main >
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/registrati" element={<Registrati />} />
            <Route path="/chi-siamo" element={<ChiSiamo />} />
            <Route path="/valutazione" element={<Valutazione />} />
            <Route path="/login" element={<Login />} />
            <Route path="/faq" element={<FAQ />} />
            <Route path="/agente" element={<Agente />} />
            <Route path="*" element={<NotFound />} />
            <Route path="/formbuy/*" element={<FormBuy />} />
            <Route path="/test-faq" element={<FaqList />} />
          </Routes>
        </main>
        <Footer />
      </BrowserRouter>
    </div>
  );
};

export default AppRoutes;