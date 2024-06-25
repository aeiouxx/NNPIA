const FullPageSpinner = () => {
  return (
    <div className="flex h-full justify-center items-center align-middle">
      <div className="w-16 h-16 border-4 border-t-4 border-t-blue-500 border-gray-200 rounded-full animate-spin"></div>
    </div>
  );
};

export default FullPageSpinner;