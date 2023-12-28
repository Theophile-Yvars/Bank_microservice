import primaBankLogo from '../res/img/primaBankLogo.png'

export default function Header({
                                 heading
                               }) {
  return (
    <div className="mb-10">
      <div className="flex justify-center">
        <img
          alt=""
          className=""
          src={primaBankLogo}/>
      </div>
      <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
        {heading}
      </h2>
    </div>
  )
}