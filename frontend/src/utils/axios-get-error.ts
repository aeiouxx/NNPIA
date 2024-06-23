import axios from 'axios';
import { CustomError } from '../types/errors';

interface ErrorMapping {
    [stausCode: number]: string;
}

export const mapErrorToMessage = (error : any, mapping : ErrorMapping = {}) => {
    var customError = error as CustomError;
    if (axios.isAxiosError(customError)) {
        if (!error.response) {
            return "Unable to process requests at this time, please try again later.";
        }
        if (!mapping[error.response.status] &&
            customError.response && customError.response.data) 
        {
            const errors = customError.response.data;
            const messages = Object.values(errors).join("\n");
            if (messages.length > 0) {
                return messages;
            }
        }
        return mapping[error.response.status] || error.response.data.message || "An unexpected error occurred, please try again later.";
    }
    else return "An unexpected error occurred, please try again later."
}
