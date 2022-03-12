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

const UsePostAPI = async (requestData: any, checkType: string, api: string) => {
  if (checkType === 'form') {
    const fData = await fetch(`${process.env.REACT_APP_TEST_API}${api}`, {
      method: 'POST',
      body: requestData,
    });

    const res = await fData.json();

    return res;
  } else if (checkType === 'json') {
    const fData = await fetch(`${process.env.REACT_APP_TEST_API}${api}`, {
      method: 'POST',
      body: JSON.stringify(requestData),
      headers: {
        'Content-Type': 'application/json',
      },
    });

    const res = await fData.json();

    return res;
  } else {
    return;
  }
};

export { useAsync, UsePostAPI };
