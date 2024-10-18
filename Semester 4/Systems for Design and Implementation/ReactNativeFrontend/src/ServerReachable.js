export const isReachable = async () =>{
  const timeout = new Promise((resolve, reject) => {
    setTimeout(reject, 5000, 'Request timed out');
  });
  const request = fetch(`https://backendmpp-vsrjvd47ea-od.a.run.app/users/connection`);
  try {
    const response = await Promise
      .race([timeout, request]);
    //console.log("isreachable",response);
    return true;
  }
  catch (error) {
    //console.log("not reachable");
    return false;
  }
}
