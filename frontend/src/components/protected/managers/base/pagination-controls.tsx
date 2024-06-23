
interface PaginationControlsProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void
}


const PaginationControls: React.FC<PaginationControlsProps> = ({ currentPage, totalPages, onPageChange: handleChange }) => {
  return (
    <div className="flex justify-between items-center mt-4">
      <button
        onClick={() => handleChange(currentPage - 1)}
        disabled={currentPage <= 1}
        className="bg-gray-300 hover:bg-gray-400 text-black p-2 rounded disabled:opacity-50"
      >
        Previous
      </button>
      <span>
        Page {currentPage} of {totalPages}
      </span>
      <button
        onClick={() => handleChange(currentPage + 1)}
        disabled={currentPage >= totalPages}
        className="bg-gray-300 hover:bg-gray-400 text-black p-2 rounded disabled:opacity-50"
      >
        Next
      </button>
    </div>
  );
};

export default PaginationControls;