import './App.css';
import {
    BrowserRouter,
    Routes,
    Route,
} from "react-router-dom";
import LoginPage from './pages/Login';
import HomePage from "./pages/Home";
import Signup from "./pages/Signup";
import History from "./pages/History";
import Nav from "./components/Nav";
import Offers from "./pages/Offers";


function App() {

    const history = () => {
        if (localStorage.getItem("client") === null) {
            return (
                <div>
                    <Nav/>
                    <div>Please LogIn</div>
                </div>
            )
        } else {
            return <History/>
        }
    }


    return (
        <div className="contain">
            <div className="">
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<HomePage/>}/>
                        <Route path="/login" element={<LoginPage/>}/>
                        <Route path="/signup" element={<Signup/>}/>
                          <Route path="/offers/:idClient" element={<Offers/>} />
                        <Route path={"/history/:idClient"} element={
                           history()
                        }/>
                    </Routes>
                </BrowserRouter>
            </div>
        </div>
    );
}

export default App;