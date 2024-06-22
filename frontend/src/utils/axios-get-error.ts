import axios from 'axios';
import { CustomError } from '../types/errors';

interface ErrorMapping {
    [stausCode: number]: string;
}

export const mapErrorToMessage = (error : any, mapping : ErrorMapping = {}) => {
    var customError = error as CustomError;
    if (axios.isAxiosError(customError)) {
        if (customError.response && customError.response.data
        ) {
            const errors = customError.response.data;
            const messages = Object.values(errors).join("\n");
            return messages;
        }
        // Assume network error if no response, might not be 
        // an entirely correct assumption
        if (!error.response) {
            return "Unable to process requests at this time, please try again later.";
        }
        if (error.response.status === 400) {
            console.log(JSON.stringify(error));
        }
        return mapping[error.response.status] || error.response.data.message || "An unexpected error occurred, please try again later.";
    }
    else return "An unexpected error occurred, please try again later."
}
