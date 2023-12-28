import Nav from "../components/Nav";
import {useState} from "react";

function HistoryPage() {
  const [transactions, setTransactions] = useState();

  const getAllTransactions = () => {
    const response = await axiox.get()
  }


  return (
    <div>
      <Nav/>
      <div className="flex items-center justify-center min-h-screen bg-white">

      </div>
    </div>
  );
}

export default HomePage;