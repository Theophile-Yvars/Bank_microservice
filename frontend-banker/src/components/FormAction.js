import React from 'react';

export default function FormAction({
                                     handleSubmit,
                                     type = 'Button',
                                     action = 'submit',
                                     text
                                   }) {
  return (
    <>
      {type === 'Button' ? ( // Conditionally render a button
        <button
          type={action} // Button's action type (e.g., 'submit' or 'button')
          className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 mt-10"
          onClick={handleSubmit} // Function to be called when the button is clicked
        >
          {text} {/* Text displayed on the button */}
        </button>
      ) : (
        <></> // An empty React fragment is returned if the type is not 'Button'
      )}
    </>
  );
}
