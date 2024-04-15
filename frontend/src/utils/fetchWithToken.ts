const fetchWithToken = async (url: string, options: RequestInit = {}) => {
  const token = localStorage.getItem('token');

  return fetch(url, {
    ...options,
    headers: {
      ...options.headers,
      Authorization: token ? `Bearer ${token}` : '',
    },
  });
};

export default fetchWithToken;
