import axios from 'axios';

interface ErrorMapping {
    [stausCode: number]: string;
}

export const mapErrorToMessage = (error : any, mapping: ErrorMapping) => {
    const resource = mapping[error.response?.status] || "Unknown";
    if (axios.isAxiosError(error)) {
        // Assume network error if no response, might not be 
        // an entirely correct assumption
        if (!error.response) {
            return "Unable to process requests at this time, please try again later.";
        }
        switch (error.response?.status) {
            case 409:
                return `${resource} already exists.`
            case 401:
            case 403:
                return `Invalid ${resource}.`
            default:
                return error.response?.data?.message 
                    || "An unexpected error occurred, please try again later.";
        }
    }
    else return "An unexpected error occurred, please try again later."
}
