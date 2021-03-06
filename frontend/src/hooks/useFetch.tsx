import { request } from 'http';
import { useReducer, useEffect } from 'react';

function reducer(state: any, action: any) {
  switch (action.type) {
    case 'LOADING':
      return {
        loading: true,
        data: null,
        error: null,
      };
    case 'SUCCESS':
      return {
        loading: false,
        data: action.data,
        error: null,
      };
    case 'ERROR':
      return {
        loading: false,
        data: null,
        error: action.error,
      };
    default:
      throw new Error(`Unhandled action type: ${action.type}`);
  }
}

function useAsync(callback: any, deps = []) {
  const [state, dispatch] = useReducer(reducer, {
    loading: false,
    data: null,
    error: false,
  });

  const fetchData = async () => {
    dispatch({ type: 'LOADING' });
    try {
      const data = await callback();
      dispatch({ type: 'SUCCESS', data });
    } catch (e) {
      dispatch({ type: 'ERROR', error: e });
    }
  };

  useEffect(() => {
    fetchData();
  }, deps);

  return [state, fetchData];
}

const postAPI: Function = async (
  requestData: any,
  checkType: string,
  api: string
) => {
  if (checkType === 'form') {
    let res;
    try {
      const fData = await fetch(`${process.env.REACT_APP_TEST_API}${api}`, {
        method: 'POST',
        body: requestData,
      });

      res = await fData.json();
    } catch (err) {
      alert(err);
    }

    return res;
  } else if (checkType === 'json') {
    const fData = await fetch(`${process.env.REACT_APP_TEST_API}${api}`, {
      method: 'POST',
      body: JSON.stringify(requestData),
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + `${window.localStorage.getItem('token')}`,
      },
    });

    if (fData.status === 200) {
      return [fData.status, null];
    }

    const res = await fData.json();

    return [fData.status, res];
  } else if (checkType === 'login') {
    const fData = await fetch(`${process.env.REACT_APP_TEST_API}${api}`, {
      method: 'POST',
      body: JSON.stringify(requestData),
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + `${window.localStorage.getItem('token')}`,
      },
    });

    const res = await fData.text();

    return [fData.status, res];
  } else {
    return;
  }
};

const getAPI = async (api: string) => {
  const fData = await fetch(`${process.env.REACT_APP_TEST_API}${api}`, {
    headers: {
      Authorization: 'Bearer ' + `${localStorage.getItem('token')}`,
    },
  });

  const res = await fData.json();

  return [fData.status, res];
};

const putAPI = async (requestData: any, type: string, api: string) => {
  if (type === 'form') {
    const fData = await fetch(`${process.env.REACT_APP_TEST_API}${api}`, {
      method: 'PUT',
      body: requestData,
      headers: {
        Authorization: 'Bearer ' + `${window.localStorage.getItem('token')}`,
      },
    });

    if (fData.status === 200) {
      return [fData.status, null];
    }

    const res = await fData.json();

    return [fData.status, res];
  } else {
    const fData = await fetch(`${process.env.REACT_APP_TEST_API}${api}`, {
      method: 'PUT',
      body: JSON.stringify(requestData),
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + `${window.localStorage.getItem('token')}`,
      },
    });

    if (fData.status === 200) {
      return [fData.status, null];
    }

    const res = await fData.json();

    return [fData.status, res];
  }
};

const deleteAPI = async (api: string) => {
  const fData = await fetch(`${process.env.REACT_APP_TEST_API}${api}`, {
    method: 'DELETE',
    headers: {
      Authorization: 'Bearer ' + `${localStorage.getItem('token')}`,
    },
  });

  if (fData.status === 200) {
    return [fData.status, null];
  }

  const res = await fData.json();

  return [fData.status, res];
};

export { useAsync, postAPI, getAPI, putAPI, deleteAPI };
