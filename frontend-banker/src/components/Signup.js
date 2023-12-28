import {useState} from 'react';
import {signupFields} from "../constants/formFields"
import FormAction from "./FormAction";
import Input from "./Input";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import Client from "../entities/Client"

const fields = signupFields;
let fieldsState = {};

fields.forEach(field => fieldsState[field.id] = '');

export default function Signup() {
  const [signupState, setSignupState] = useState(fieldsState);
  const navigate = useNavigate();

  const handleChange = (e) => setSignupState({...signupState, [e.target.id]: e.target.value});

  const handleSubmit = (e) => {
    e.preventDefault();
    createAccount(signupState)
  }

  //handle Signup API Integration here
  const createAccount = async (loginData) => {
    const client_firstname = loginData["first-name"];
    const client_lastname = loginData["last-name"];
    const client_name = client_firstname + client_lastname;

    //login success

    const url = "http://localhost:8082/clients/register/" + client_name;
    const response = await axios.post(url)
    const clientData = response.data;

    console.log(clientData);

    const newClientAccount = new Client(
      clientData.id,
      clientData.accountId
    );

    console.log(newClientAccount);

    alert(`Account created \nYour id is ${newClientAccount.accountId}`);
    localStorage.setItem("client", newClientAccount.accountId);
    navigate("/");
  }

  return (
    <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
      <div className="">
        {
          fields.map(field =>
            <Input
              key={field.id}
              handleChange={handleChange}
              value={signupState[field.id]}
              labelText={field.labelText}
              labelFor={field.labelFor}
              id={field.id}
              name={field.name}
              type={field.type}
              isRequired={field.isRequired}
              placeholder={field.placeholder}
            />
          )
        }
        <FormAction handleSubmit={handleSubmit} text="Signup"/>
      </div>
    </form>
  )
}