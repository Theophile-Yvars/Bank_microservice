import './App.css';
import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";
import LoginPage from './pages/Login';
import HomePage from "./pages/Home";
import Signup from "./pages/Signup";
import ProductList from "./components/Product";
import AccountList from "./components/Accounts";
import TransactionList from "./components/Transactions";
import Statistics from './components/Stats';
import Offers from "./components/Offers";

function App() {


  return (
      <div className="contain">
        <div className="">
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<HomePage/>} />
              <Route path="/products" element={<ProductList/>} />
              <Route path='/accounts' element={<AccountList/>}/> 
              <Route path="/transactions/:clientId" element={<TransactionList/>} />
              <Route path="/statistics" element={<Statistics/>} />
              <Route path="/offers/:clientId" element={<Offers/>} />
            </Routes>
          </BrowserRouter>
        </div>
      </div>
  );
}

export default App;